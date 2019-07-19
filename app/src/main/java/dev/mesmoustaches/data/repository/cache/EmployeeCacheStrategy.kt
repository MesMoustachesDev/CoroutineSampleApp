package dev.mesmoustaches.data.repository.cache

import dev.mesmoustaches.data.common.CacheStrategy
import dev.mesmoustaches.data.model.EmployeeData
import java.util.*

class EmployeeCacheStrategy: CacheStrategy<EmployeeData> {
    private var time: Long = 0

    override fun isCacheValid(): Boolean {
        return (Date().time - time) < 1 * 60 * 1000
    }

    override fun newCacheSet() {
        time = Date().time
    }
}