package dev.mesmoustaches.domain.usecase

import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.data.model.getout.Facet
import dev.mesmoustaches.data.model.getout.FacetGroup
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import kotlinx.coroutines.runBlocking

class SetFiltersUseCase(
    private val eventRepository: EventRepository
) {
    suspend fun execute(filters: List<FilterCategoryDomain>) {
        val filterData = filters.map { filterCategoryDomain ->
            FacetGroup(id = filterCategoryDomain.id,
                facets = filterCategoryDomain.filters?.map {
                    Facet(count = 0,
                        path = it.path,
                        name = it.name,
                        selected = it.selected,
                        state = null)
                })
        }
        runBlocking {
            eventRepository.setFilters(filterData)
        }
        eventRepository
            .fetchEvents(
                forceUpdate = true,
                loadMore = false
            )
    }
}