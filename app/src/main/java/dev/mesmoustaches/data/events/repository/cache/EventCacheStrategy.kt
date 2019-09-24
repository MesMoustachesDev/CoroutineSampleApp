package dev.mesmoustaches.data.events.repository.cache

import android.content.Context
import android.content.SharedPreferences
import dev.mesmoustaches.data.common.CacheStrategy
import dev.mesmoustaches.data.model.getout.RecordData
import java.util.*

class EventCacheStrategy(context: Context): CacheStrategy<RecordData> {
    private val sharedPref: SharedPreferences = context.getSharedPreferences(
        EVENT_CACHE_STRATEGY, Context.MODE_PRIVATE)

    override fun isCacheValid(): Boolean {
        val lastRefresh = sharedPref.getLong(EVENT_CACHE_STRATEGY_LAST_REFRESH, -1)
        return (Date().time - lastRefresh) < 1 * 3600 * 1000
    }

    override fun newCacheSet() {
        with (sharedPref.edit()) {
            putLong(EVENT_CACHE_STRATEGY_LAST_REFRESH, Date().time)
            commit()
        }
    }

    companion object {
        const val EVENT_CACHE_STRATEGY = "EVENT_CACHE_STRATEGY"

        const val EVENT_CACHE_STRATEGY_LAST_REFRESH = "EVENT_CACHE_STRATEGY_LAST_REFRESH"
    }
}