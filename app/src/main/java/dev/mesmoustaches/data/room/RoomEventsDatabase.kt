package dev.mesmoustaches.data.room

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.common.Specification
import dev.mesmoustaches.data.model.getout.RecordData

class RoomEventsDatabase(
    private val eventDao: EventsDao
) : EventsDataSource {

    override fun add(item: RecordData) {
        eventDao.addEvent(item)
    }

    override fun add(items: Iterable<RecordData>) {
        eventDao.addEventList(items.toList())
    }

    override suspend fun updateAndAdd(items: Iterable<RecordData>) {
        eventDao.clearAndAdd(items.toList())
    }

    override fun update(item: RecordData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(item: RecordData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(specification: Specification) {
        when (specification) {
            is DataSource.Spec.ByRef -> eventDao.removeEvent(specification.id)
            is DataSource.Spec.All -> eventDao.clearEventList()
        }
    }

    override fun queryId(specification: String): LiveData<RecordData> =
        eventDao
            .getEventsWithRef(specification)

    override fun queryList(specification: Specification): LiveData<List<RecordData>> =
        eventDao
            .getEvents()

    override suspend fun queryListNoLiveData(specification: Specification): List<RecordData> =
        eventDao
            .getEventsNoLiveData()
}