package dev.mesmoustaches.data.di

import androidx.room.Room
import dev.mesmoustaches.data.common.DataSource
import dev.mesmoustaches.data.model.EmployeeData
import dev.mesmoustaches.data.room.DataBase
import dev.mesmoustaches.data.room.RoomEmployeeDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), DataBase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<DataBase>().employeeDao() }
    single { RoomEmployeeDatabase(get()) as DataSource<EmployeeData> }
}