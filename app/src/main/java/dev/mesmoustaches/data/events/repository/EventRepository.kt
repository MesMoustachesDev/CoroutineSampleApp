package dev.mesmoustaches.data.events.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.model.getout.FacetGroup
import dev.mesmoustaches.data.model.getout.RecordData

interface EventRepository {
    /**
     * Fetch user's animals
     */
    suspend fun fetchEvents(
        forceUpdate: Boolean = false,
        loadMore: Boolean = false
    )

    fun getEvents(): LiveData<List<RecordData>>
    suspend fun fetchMoreEvents(start: Int, rows: Int)
    fun getFilters(): LiveData<List<FacetGroup>>
    suspend fun setFilters(filters: List<FacetGroup>)
    fun getLoading(): LiveData<Boolean>
}