package dev.mesmoustaches.data.events.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import dev.mesmoustaches.data.common.CacheStrategy
import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.events.remote.ApiService
import dev.mesmoustaches.data.model.getout.FacetGroup
import dev.mesmoustaches.data.model.getout.RecordData
import dev.mesmoustaches.data.model.getout.toDomain
import dev.mesmoustaches.data.room.EventsDataSource
import dev.mesmoustaches.data.room.FiltersDataSource
import dev.mesmoustaches.domain.model.EventsBusiness
import dev.mesmoustaches.domain.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class EventRepositoryImpl(
    private val apiService: ApiService,
    private val localDataSource: EventsDataSource,
    private val filterDataSource: FiltersDataSource,
    private val cacheStrategy: CacheStrategy<RecordData>
) : EventRepository {

    @Volatile
    private var fetchRunning = false

    private val privateEvents = localDataSource.queryList(DataSource.Spec.All())
    private val events = MediatorLiveData<EventsBusiness>()

    private val filters = filterDataSource.queryList(DataSource.Spec.All())

    private val loading = MutableLiveData<Boolean>()
    private val hasMore = MutableLiveData<Boolean>()

    override fun getEvents() = events

    override fun getLoading(): LiveData<Boolean> = loading

    override fun getFilters(): LiveData<List<FacetGroup>> = filters

    override fun getPaginationSize() = 50

    init {
        events.addSource(privateEvents) { list ->
            events.postValue(EventsBusiness(list.map { it.toDomain() }, hasMore.value ?: false))
        }
        events.addSource(hasMore) { hasMore ->
            events.postValue(EventsBusiness(privateEvents.value?.map { it.toDomain() } ?: listOf(), hasMore))
        }
        hasMore.postValue(true)
    }

    override suspend fun setFilters(filters: List<FacetGroup>) {
        withContext(Dispatchers.IO) {
            filterDataSource.updateAndAdd(filters)
        }
    }

    override suspend fun fetchEvents(
        forceUpdate: Boolean,
        loadMore: Boolean
    ) {
        if (!cacheStrategy.isCacheValid() || forceUpdate || loadMore) {
            loading.postValue(true)
            Timber.d("Loading from api")
            fetchRunning = true
            withContext(Dispatchers.IO) {
                try {
                    val filtersFromDB = filterDataSource.queryListNoLiveData(DataSource.Spec.All())
                    val filterMap = HashMap<String, String>()
                    filtersFromDB.let { filterList ->
                        filterList.forEach { facetGroup ->
                            if (facetGroup.facets != null) {
                                val nonNullFacets = facetGroup.facets.filterNotNull()
                                nonNullFacets
                                    .filter { it.selected }
                                    .forEach {
                                        // fix issue where facet sent by server is not the one server is waiting for
                                        filterMap["refine.${facetGroup.id}"] = it.path
                                    }
                            }
                        }
                    }
                    val result = apiService.getEvents(
                        start = if (!loadMore) 0 else privateEvents.value?.size ?: 0,
                        rows = getPaginationSize(),
                        refine = filterMap
                    )
                    if (!loadMore) {
                        result.records?.let {
                            localDataSource.updateAndAdd(it.filterNotNull())
                            cacheStrategy.newCacheSet()
                        }
                    } else {
                        result.records?.let {
                            localDataSource.add(it.filterNotNull())
                            cacheStrategy.newCacheSet()
                        }
                    }

                    hasMore.postValue((result.records?.size ?:0) > 0)

                    result.facetGroups?.let {
                        // FIXME Dont remove existing filters
                        if (filters.value == null || filters.value?.isEmpty() == true) {
                            filterDataSource.add(it.filterNotNull())
                        }
                    }

                } catch (error: Throwable) {
                    throw error
                } finally {
                    loading.postValue(false)
                    fetchRunning = false
                }
            }
        } else {
            Timber.d("Loading from cache")
        }
    }

    override suspend fun fetchMoreEvents(start: Int, rows: Int) {
        loading.postValue(true)
        if (fetchRunning) return
        Timber.d("Loading from api")
        fetchRunning = true
        withContext(Dispatchers.IO) {
            try {
                val result = apiService.getEvents(start = start, rows = rows)
                result.records?.let {
                    localDataSource.add(it.filterNotNull())
                    cacheStrategy.newCacheSet()
                }
            } catch (error: Throwable) {
                throw error
            } finally {
                loading.postValue(false)
                fetchRunning = false
            }
        }
    }
}