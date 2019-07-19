package dev.mesmoustaches.data.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.model.EmployeeData
import dev.mesmoustaches.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepositoryImpl(
    private val apiService: ApiService,
    private val localDataSource: DataSource<EmployeeData>
) : EmployeeRepository {

    @Volatile
    private var fetchEmployeesRunning = false

    private val employees = localDataSource.queryList(DataSource.Spec.All())

    override fun getEmployees(): LiveData<List<EmployeeData>> {
        return employees
    }

    override suspend fun fetchEmployees() {
        if (fetchEmployeesRunning) return
        fetchEmployeesRunning = true
        withContext(Dispatchers.IO) {
            try {
                val result = apiService.getEmployees()
                localDataSource.add(result)
            } catch (error: Throwable) {
                throw error
            }
        }
        fetchEmployeesRunning = false
    }
}