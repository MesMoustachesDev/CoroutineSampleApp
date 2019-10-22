package dev.mesmoustaches.domain.usecase

import dev.mesmoustaches.domain.repository.EventRepository
import dev.mesmoustaches.data.model.getout.Facet
import dev.mesmoustaches.data.model.getout.FacetGroup
import dev.mesmoustaches.domain.model.FilterCategoryDomain

class SetFiltersUseCase(
    private val eventRepository: EventRepository
) {
    suspend fun execute(filters: List<FilterCategoryDomain>) {
        val filterData = filters.map { filterCategoryDomain ->
            FacetGroup(id = filterCategoryDomain.id,
                facets = filterCategoryDomain.filters?.map {
                    Facet(
                        count = 0,
                        path = it.path,
                        name = it.name,
                        selected = it.selected,
                        state = null
                    )
                })
        }
        eventRepository.setFilters(filterData)
        eventRepository.fetchEvents(forceUpdate = true)
    }
}