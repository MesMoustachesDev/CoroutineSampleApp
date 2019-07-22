package dev.mesmoustaches.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mesmoustaches.data.model.getout.RecordData

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
    fun getEvents(): LiveData<List<RecordData>>

    @Query("SELECT * from events WHERE recordid = :ref")
    fun getEventsWithRef(ref: String): LiveData<RecordData>
}