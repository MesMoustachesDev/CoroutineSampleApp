package dev.mesmoustaches.data.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.model.EmployeeData

interface EmployeeRepository {
    /**
     * Fetch user's animals
     */
    suspend fun fetchEmployees(forceUpdate: Boolean = false)

    fun getEmployees(): LiveData<List<EmployeeData>>
}