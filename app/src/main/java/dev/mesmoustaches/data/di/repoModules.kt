package dev.mesmoustaches.data.di

import dev.mesmoustaches.data.repository.EmployeeRepository
import dev.mesmoustaches.data.repository.EmployeeRepositoryImpl
import org.koin.dsl.module

val repoModules = module {
    single { EmployeeRepositoryImpl(get(), get()) as EmployeeRepository }
}