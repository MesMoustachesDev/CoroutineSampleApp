package dev.mesmoustaches.domain.usecase

import dev.mesmoustaches.coroutines.CoroutineUseCase
import dev.mesmoustaches.domain.repository.EventRepository
import dev.mesmoustaches.domain.model.EventDomain
import kotlinx.coroutines.flow.map
import timber.log.Timber

class GetEventDetailsUseCase(
    private val eventRepository: EventRepository
) : CoroutineUseCase<GetEventDetailsUseCase.Params, List<EventDomain>>() {

    override suspend fun createCoroutine(input: Params?) {
        try {
            eventRepository
                .fetchEvents(
                    false,
                    loadMore = false
                )
        } catch (e: Exception) {
            Timber.e(e, "Error getting employees")
            throw e
        }
    }

    fun getStream(id: String?) = eventRepository.getEvents().map { events ->
        val event = events.events.firstOrNull { it.id == id }
        event ?: EventDomain.NotFoundEvent()
    }

    data class Params(
        val id: String
    )
}