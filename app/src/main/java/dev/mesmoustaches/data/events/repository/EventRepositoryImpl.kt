package dev.mesmoustaches.data.events.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.common.CacheStrategy
import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.events.remote.ApiService
import dev.mesmoustaches.data.model.getout.FacetGroup
import dev.mesmoustaches.data.model.getout.RecordData
import dev.mesmoustaches.data.room.EventsDataSource
import dev.mesmoustaches.data.room.FiltersDataSource
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

    private val events = localDataSource.queryList(DataSource.Spec.All())
    private val filters = filterDataSource.queryList(DataSource.Spec.All())

    override fun getEvents(): LiveData<List<RecordData>> {
        return events
    }

    override fun getFilters(): LiveData<List<FacetGroup>> {
        return filters
    }

    override suspend fun fetchEvents(forceUpdate: Boolean,
                                     loadMore: Boolean) {
        if (fetchRunning) return
        if (!cacheStrategy.isCacheValid() || forceUpdate || loadMore) {
            Timber.d("Loading from api")
            fetchRunning = true
            withContext(Dispatchers.IO) {
                try {
                    val result = apiService.getEvents(
                        start = if (!loadMore) 0 else events.value?.size ?: 0,
                        rows = 50
                    )
                    if (!loadMore) {
                        localDataSource.remove(DataSource.Spec.All())
                    }
                    result.records?.let {
                        localDataSource.add(it.filterNotNull())
                        cacheStrategy.newCacheSet()
                    }

                    result.facetGroups?.let {
                        filterDataSource.remove(DataSource.Spec.All())
                        filterDataSource.add(it.filterNotNull())
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
            } catch (error: Throwable) {
                throw error
            } finally {
                fetchRunning = false
            }
        }
    }
}