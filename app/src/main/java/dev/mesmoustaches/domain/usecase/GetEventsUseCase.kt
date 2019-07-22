package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.coroutines.CoroutineUseCase
import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.domain.model.EventDomain
import dev.mesmoustaches.domain.model.toDomain
import timber.log.Timber

class GetEventsUseCase(
    private val eventRepository: EventRepository
) : CoroutineUseCase<GetEventsUseCase.Params, List<EventDomain>>() {

    val data = Transformations.map(eventRepository.getEvents()) { list ->
        list.map { it.toDomain() }
    }

    override suspend fun createCoroutine(input: GetEventsUseCase.Params?) {
        try {
            eventRepository
                .fetchEvents(input?.forceUpdate ?: false,
                    input?.loadMore ?: false)
        } catch (e: Exception) {
            Timber.e(e, "Error getting employees")
            throw e
        }
    }

    data class Params(
        val forceUpdate: Boolean = false,
        val loadMore: Boolean = false
    )
}