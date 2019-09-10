package dev.mesmoustaches.domain.model

import dev.mesmoustaches.data.model.getout.RecordData
import java.text.SimpleDateFormat

sealed class EventDomain {
    abstract val id: String
    abstract val title: String
    abstract val description: String
    abstract val timeStamp: Long
    abstract val dateText: String
    abstract val image: String?

    data class EventDomainData(
        override val id: String,
        override val title: String,
        override val description: String,
        override val timeStamp: Long,
        override val dateText: String,
        override val image: String?
    ): EventDomain()

    data class NotFoundEvent(
        override val id: String = "",
        override val title: String = "Not found",
        override val description: String = "not found",
        override val timeStamp: Long = -1,
        override val dateText: String = "not found",
        override val image: Nothing? = null
    ): EventDomain()
}

fun RecordData.toDomain(): EventDomain {

/* date formatter in local timezone */
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'")
    return EventDomain.EventDomainData(
        id = recordid,
        title = fields?.title ?: "",
        description = fields?.description ?: "",
        timeStamp = sdf.parse(recordTimestamp).time,
        dateText = fields?.dateDescription ?: "",
        image = fields?.coverUrl
    )
}