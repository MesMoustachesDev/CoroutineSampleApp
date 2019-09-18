package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.domain.model.toDomain
import timber.log.Timber

class GetFiltersUseCase(
    eventRepository: EventRepository
) {
    val loading = eventRepository.getLoading()

    val data = Transformations.map(eventRepository.getFilters()) { list ->
        Timber.d("GetFiltersUseCase got new data")
        list.map { it.toDomain() }
    }
}