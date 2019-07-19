package dev.mesmoustaches.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.mesmoustaches.data.model.EmployeeData

@Database(entities = [EmployeeData::class], version = 1, exportSchema = false)

abstract class DataBase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}