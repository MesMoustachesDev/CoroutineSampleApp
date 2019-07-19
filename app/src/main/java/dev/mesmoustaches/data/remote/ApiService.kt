package dev.mesmoustaches.data.remote

import dev.mesmoustaches.data.model.EmployeeData
import retrofit2.http.GET

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): List<EmployeeData>
}