package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.data.model.getout.toDomain
import dev.mesmoustaches.domain.repository.EventRepository
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