package dev.mesmoustaches.domain.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.model.getout.FacetGroup
import dev.mesmoustaches.domain.model.EventsBusiness
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    /**
     * Fetch user's animals
     */
    suspend fun fetchEvents(
        forceUpdate: Boolean = false,
        loadMore: Boolean = false
    )

    fun getEvents(): Flow<EventsBusiness>
    suspend fun fetchMoreEvents(start: Int, rows: Int)
    fun getFilters(): Flow<List<FacetGroup>>
    suspend fun setFilters(filters: List<FacetGroup>)
    fun getPaginationSize(): Int
}