package dev.mesmoustaches.data.common

import androidx.lifecycle.LiveData

interface DataSource<T> {
    fun add(item: T)
    fun add(items: Iterable<T>)
    fun update(item: T)
    fun updateAndAdd(items: Iterable<T>)
    fun remove(item: T)
    fun remove(specification: Specification)
    fun queryId(specification: String): LiveData<T>
    fun queryList(specification: Specification): LiveData<List<T>>
    fun queryListNoLiveData(specification: Specification): List<T>

    class Spec : Specification {
        data class ByRef(val id: String) : Specification
        class All : Specification
    }
}

interface Specification {
    object Empty : Specification
}
