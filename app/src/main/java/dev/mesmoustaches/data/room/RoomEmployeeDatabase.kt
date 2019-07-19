package dev.mesmoustaches.data.room

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.common.Specification
import dev.mesmoustaches.data.model.EmployeeData

class RoomEmployeeDatabase(
    private val employeeDao: EmployeeDao
) : DataSource<EmployeeData> {

    override fun add(item: EmployeeData) {
        employeeDao.addEmployee(item)
    }

    override fun add(items: Iterable<EmployeeData>) {
        employeeDao.addEmployeeList(items.toList())
    }

    override fun update(item: EmployeeData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(item: EmployeeData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(specification: Specification) {
        when (specification) {
            is DataSource.Spec.ByRef -> employeeDao.removeEmployee(specification.id)
            is DataSource.Spec.All -> employeeDao.clearEmployeeList()
        }
    }

    override fun queryId(specification: String): LiveData<EmployeeData> =
        employeeDao
            .getEmployeesWithRef(specification)

    override fun queryList(specification: Specification): LiveData<List<EmployeeData>> =
        employeeDao
            .getEmployees()
}