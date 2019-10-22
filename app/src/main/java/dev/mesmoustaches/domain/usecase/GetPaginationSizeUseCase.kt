package dev.mesmoustaches.domain.usecase

import dev.mesmoustaches.domain.repository.EventRepository

class GetPaginationSizeUseCase(
    private val eventRepository: EventRepository
) {

    val data = eventRepository.getPaginationSize()
}