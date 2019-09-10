package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.domain.model.toDomain

class GetFiltersUseCase(
    eventRepository: EventRepository
) {
    val loading = eventRepository.getLoading()

    val data = Transformations.map(eventRepository.getFilters()) { list ->
        list.map { it.toDomain() }
    }
}