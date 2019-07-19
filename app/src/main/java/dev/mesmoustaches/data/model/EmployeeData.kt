package dev.mesmoustaches.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "employees")
data class EmployeeData(
    @SerializedName("employee_age")
    @ColumnInfo(name = "employee_age")
    val employeeAge: String?,
    @SerializedName("employee_name")
    @ColumnInfo(name = "employee_name")
    val employeeName: String?,
    @ColumnInfo(name = "employee_salary")
    @SerializedName("employee_salary")
    val employeeSalary: String?,
    @SerializedName("id")
    @PrimaryKey
    val id: String,
    @SerializedName("profile_image")
    @ColumnInfo(name = "profile_image")
    val profileImage: String?
)