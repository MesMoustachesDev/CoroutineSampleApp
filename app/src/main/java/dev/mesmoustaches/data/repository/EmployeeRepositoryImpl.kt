package dev.mesmoustaches.data.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.common.CacheStrategy
import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.model.EmployeeData
import dev.mesmoustaches.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class EmployeeRepositoryImpl(
    private val apiService: ApiService,
    private val localDataSource: DataSource<EmployeeData>,
    private val cacheStrategy: CacheStrategy<EmployeeData>
) : EmployeeRepository {

    @Volatile
    private var fetchEmployeesRunning = false

    private val employees = localDataSource.queryList(DataSource.Spec.All())

    override fun getEmployees(): LiveData<List<EmployeeData>> {
        return employees
    }

    override suspend fun fetchEmployees(forceUpdate: Boolean) {
        if (fetchEmployeesRunning) return
        if (!cacheStrategy.isCacheValid() || forceUpdate) {
            Timber.d("Loading from api")
            fetchEmployeesRunning = true
            withContext(Dispatchers.IO) {
                try {
                    val result = apiService.getEmployees()
                    localDataSource.remove(DataSource.Spec.All())
                    localDataSource.add(result)
                    cacheStrategy.newCacheSet()
                } catch (error: Throwable) {
                    throw error
                } finally {
                    fetchEmployeesRunning = false
                }
            }
        } else {
            Timber.d("Loading from cache")
        }
    }
}