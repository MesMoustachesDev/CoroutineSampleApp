package dev.mesmoustaches.data.common

import kotlinx.coroutines.flow.Flow

interface DataSource<T> {
    fun add(item: T)
    fun add(items: Iterable<T>)
    fun update(item: T)
    suspend fun updateAndAdd(items: Iterable<T>)
    fun remove(item: T)
    fun remove(specification: Specification)
    fun queryId(specification: String): Flow<T>
    fun queryList(specification: Specification): Flow<List<T>>
    suspend fun queryListNoLiveData(specification: Specification): List<T>

    class Spec : Specification {
        data class ByRef(val id: String) : Specification
        class All : Specification
    }
}

interface Specification {
    object Empty : Specification
}
