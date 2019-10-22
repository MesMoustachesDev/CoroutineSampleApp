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
    abstract val address: String?
    abstract val position: Pair<Double, Double>?
    abstract val phone: String?
    abstract val mail: String?
    abstract val facebook: String?

    data class EventDomainData(
        override val id: String,
        override val title: String,
        override val description: String,
        override val timeStamp: Long,
        override val dateText: String,
        override val image: String?,
        override val address: String?,
        override val position: Pair<Double, Double>?,
        override val phone: String?,
        override val mail: String?,
        override val facebook: String?
    ): EventDomain()

    data class NotFoundEvent(
        override val id: String = "",
        override val title: String = "Not found",
        override val description: String = "not found",
        override val timeStamp: Long = -1,
        override val dateText: String = "not found",
        override val image: Nothing? = null,
        override val address: String? = null,
        override val position: Pair<Double, Double> ?= null,
        override val phone: String? = null,
        override val mail: String? = null,
        override val facebook: String? = null
    ): EventDomain()
}

fun String.toLinkBalise() = "<a href=\"$this\">Website</a>"