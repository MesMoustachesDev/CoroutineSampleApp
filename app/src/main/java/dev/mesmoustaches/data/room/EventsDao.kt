package dev.mesmoustaches.data.room

import androidx.room.*
import dev.mesmoustaches.data.model.getout.RecordData
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(employee: RecordData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEventList(employees: List<RecordData>)

    @Query("DELETE FROM events WHERE recordid=:ref")
    fun removeEvent(ref: String)

    @Query("DELETE FROM events")
    fun clearEventList()

    @Query("SELECT * from events")
    fun getEvents(): Flow<List<RecordData>>

    @Query("SELECT * from events WHERE recordid = :ref")
    fun getEventsWithRef(ref: String): Flow<RecordData>

    @Query("SELECT * from events")
    fun getEventsNoLiveData(): List<RecordData>

    @Transaction
    fun clearAndAdd(employees: List<RecordData>) {
        clearEventList()
        addEventList(employees)
    }
}