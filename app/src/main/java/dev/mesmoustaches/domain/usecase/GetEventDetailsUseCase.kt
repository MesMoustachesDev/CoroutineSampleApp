package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.coroutines.CoroutineUseCase
import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.domain.model.EventDomain
import dev.mesmoustaches.domain.model.toDomain
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

    fun getLiveData(id: String?) = Transformations.map(eventRepository.getEvents()) { list ->
        val event = list.firstOrNull { it.recordid == id }
        event?.toDomain() ?: EventDomain.NotFoundEvent()
    }

    data class Params(
        val id: String
    )
}