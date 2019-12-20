package dev.mesmoustaches.data.room

import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.common.Specification
import dev.mesmoustaches.data.model.getout.FacetGroup
import kotlinx.coroutines.flow.Flow

class RoomFilterGroupsDatabase(
    private val filtersDao: FilterGroupsDao
) : FiltersDataSource {
    override suspend fun updateAndAdd(items: Iterable<FacetGroup>) {
        filtersDao.clearAndAdd(items.toList())
    }

    override fun add(item: FacetGroup) {
        filtersDao.addFilterGroup(item)
    }

    override fun add(items: Iterable<FacetGroup>) {
        filtersDao.addFilterGroupList(items.toList())
    }

    override fun update(item: FacetGroup) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(item: FacetGroup) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(specification: Specification) {
        when (specification) {
            is DataSource.Spec.ByRef -> filtersDao.removeFilterGroup(specification.id)
            is DataSource.Spec.All -> filtersDao.clearFilterGroupList()
        }
    }

    override fun queryId(specification: String): Flow<FacetGroup> =
        filtersDao
            .getFilterGroupsWithRef(specification)

    override fun queryList(specification: Specification): Flow<List<FacetGroup>> =
        filtersDao
            .getFilterGroups()

    override suspend fun queryListNoLiveData(specification: Specification): List<FacetGroup> =
        filtersDao
            .getFilterGroupsNoLiveData()
}