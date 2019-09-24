package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.domain.model.toDomain

class GetPaginationSizeUseCase(
    private val eventRepository: EventRepository
) {

    val data = eventRepository.getPaginationSize()
}