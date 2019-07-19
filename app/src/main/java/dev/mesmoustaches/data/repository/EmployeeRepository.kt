package dev.mesmoustaches.data.repository

import androidx.lifecycle.LiveData
import dev.mesmoustaches.data.model.EmployeeData

interface EmployeeRepository {
    /**
     * Fetch user's animals
     */
    suspend fun fetchEmployees()

    fun getEmployees(): LiveData<List<EmployeeData>>
}