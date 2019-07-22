package dev.mesmoustaches.domain.model

import dev.mesmoustaches.data.model.getout.RecordData
import java.text.SimpleDateFormat

data class EventDomain (
    val id: String,
    val title: String,
    val description: String,
    val timeStamp: Long,
    val dateText: String,
    val image: String?
)
fun RecordData.toDomain(): EventDomain {

/* date formatter in local timezone */
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'")
    return EventDomain(
        id = recordid,
        title = fields?.title ?: "",
        description = fields?.description ?: "",
        timeStamp = sdf.parse(recordTimestamp).time,
        dateText = fields?.dateDescription ?: "",
        image = fields?.coverUrl
    )
}