package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.coroutines.CoroutineUseCase
import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.domain.model.EventDomain
import dev.mesmoustaches.domain.model.toDomain
import timber.log.Timber

class GetFiltersUseCase(
    private val eventRepository: EventRepository
) {

    val data = Transformations.map(eventRepository.getFilters()) { list ->
        list.map { it.toDomain() }
    }
}