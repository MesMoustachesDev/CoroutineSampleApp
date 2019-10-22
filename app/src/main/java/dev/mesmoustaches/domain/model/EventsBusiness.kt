package dev.mesmoustaches.domain.model

data class EventsBusiness (
    val events: List<EventDomain>,
    val hasMore: Boolean
)