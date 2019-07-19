package dev.mesmoustaches.data.common

interface CacheStrategy<T> {
    fun isCacheValid(): Boolean
    fun newCacheSet()
}