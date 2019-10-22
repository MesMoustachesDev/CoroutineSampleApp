package dev.mesmoustaches.domain.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.model.getout.FacetGroup
import dev.mesmoustaches.data.model.getout.RecordData
import dev.mesmoustaches.domain.model.EventsBusiness

interface EventRepository {
    /**
     * Fetch user's animals
     */
    suspend fun fetchEvents(
        forceUpdate: Boolean = false,
        loadMore: Boolean = false
    )

    fun getEvents(): LiveData<EventsBusiness>
    suspend fun fetchMoreEvents(start: Int, rows: Int)
    fun getFilters(): LiveData<List<FacetGroup>>
    suspend fun setFilters(filters: List<FacetGroup>)
    fun getLoading(): LiveData<Boolean>
    fun getPaginationSize(): Int
}