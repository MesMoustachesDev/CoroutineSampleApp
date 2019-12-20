package dev.mesmoustaches.data.room

import androidx.room.*
import dev.mesmoustaches.data.model.getout.FacetGroup
import kotlinx.coroutines.flow.Flow

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
    fun getFilterGroups(): Flow<List<FacetGroup>>

    @Query("SELECT * from filters_group WHERE path = :ref")
    fun getFilterGroupsWithRef(ref: String): Flow<FacetGroup>

    @Query("SELECT * from filters_group")
    fun getFilterGroupsNoLiveData(): List<FacetGroup>

    @Transaction
    suspend fun clearAndAdd(filters: List<FacetGroup>) {
        clearFilterGroupList()
        addFilterGroupList(filters)
    }
}