package dev.mesmoustaches.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.mesmoustaches.data.model.EmployeeData

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEmployee(employee: EmployeeData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEmployeeList(employees: List<EmployeeData>)

    @Query("DELETE FROM employees WHERE id=:ref")
    fun removeEmployee(ref: String)

    @Query("DELETE FROM employees")
    fun clearEmployeeList()

    @Query("SELECT * from employees")
    fun getEmployees(): LiveData<List<EmployeeData>>

    @Query("SELECT * from employees WHERE id = :ref")
    fun getEmployeesWithRef(ref: String): LiveData<EmployeeData>
}