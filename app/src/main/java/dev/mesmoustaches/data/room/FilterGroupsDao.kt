package dev.mesmoustaches.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mesmoustaches.data.model.getout.FacetGroup

@Dao
interface FilterGroupsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFilterGroup(filter: FacetGroup)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFilterGroupList(filters: List<FacetGroup>)

    @Query("DELETE FROM filters_group WHERE path=:ref")
    fun removeFilterGroup(ref: String)

    @Query("DELETE FROM filters_group")
    fun clearFilterGroupList()

    @Query("SELECT * from filters_group")
    fun getFilterGroups(): LiveData<List<FacetGroup>>

    @Query("SELECT * from filters_group WHERE path = :ref")
    fun getFilterGroupsWithRef(ref: String): LiveData<FacetGroup>

    @Query("SELECT * from filters_group")
    fun getFilterGroupsNoLiveData(): List<FacetGroup>
}