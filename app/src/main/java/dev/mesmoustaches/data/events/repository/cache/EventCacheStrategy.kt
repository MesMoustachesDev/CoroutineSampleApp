package dev.mesmoustaches.data.events.repository.cache

import dev.mesmoustaches.data.common.CacheStrategy
import dev.mesmoustaches.data.model.getout.RecordData
import java.util.*

class EventCacheStrategy: CacheStrategy<RecordData> {
    private var time: Long = 0

    override fun isCacheValid(): Boolean {
        return (Date().time - time) < 1 * 60 * 1000
    }

    override fun newCacheSet() {
        time = Date().time
    }
}