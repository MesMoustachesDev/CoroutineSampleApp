package dev.mesmoustaches.domain.usecase

import dev.mesmoustaches.data.model.getout.toDomain
import dev.mesmoustaches.domain.repository.EventRepository
import kotlinx.coroutines.flow.map
import timber.log.Timber

class GetFiltersUseCase(
    eventRepository: EventRepository
) {
    val data = eventRepository.getFilters().map { list ->
        Timber.d("GetFiltersUseCase got new data")
        list.map { it.toDomain() }
    }
}