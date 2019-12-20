package dev.mesmoustaches.data.events.repository

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    private var events: Flow<EventsBusiness>

    private val filters = filterDataSource.queryList(DataSource.Spec.All())

    private var hasMore = true
    private var listSize: Int = 0

    override fun getEvents() = events

    override fun getFilters(): Flow<List<FacetGroup>> = filters

    override fun getPaginationSize() = 50

    init {
        events = privateEvents.map { list ->
            listSize = list.size
            EventsBusiness(list.map { it.toDomain() }, hasMore)
        }
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
                        start = if (!loadMore) 0 else listSize,
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

                    result.facetGroups?.let {
                        // FIXME Dont remove existing filters
                        if (filtersFromDB.isEmpty()) {
                            filterDataSource.add(it.filterNotNull())
                        }
                    }

                } catch (error: Throwable) {
                    throw error
                } finally {
                    fetchRunning = false
                }
            }
        } else {
            Timber.d("Loading from cache")
        }
    }

    override suspend fun fetchMoreEvents(start: Int, rows: Int) {
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
                hasMore = (result.records?.size ?:0) > 0
            } catch (error: Throwable) {
                throw error
            } finally {
                fetchRunning = false
            }
        }
    }
}