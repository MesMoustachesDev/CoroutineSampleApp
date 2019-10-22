package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.coroutines.CoroutineUseCase
import dev.mesmoustaches.domain.repository.EventRepository
import dev.mesmoustaches.domain.model.EventDomain
import timber.log.Timber

class GetEventsUseCase(
    private val eventRepository: EventRepository
) : CoroutineUseCase<GetEventsUseCase.Params, List<EventDomain>>() {

    val data = eventRepository.getEvents()

    val loading = eventRepository.getLoading()

    override suspend fun createCoroutine(input: Params?) {
        try {
            eventRepository
                .fetchEvents(
                    input?.forceUpdate ?: false,
                    input?.loadMore ?: false
                )
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