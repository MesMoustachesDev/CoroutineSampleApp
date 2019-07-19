package dev.mesmoustaches.di

import dev.mesmoustaches.domain.usecase.EmployeesLiveDataUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory { EmployeesLiveDataUseCase(get(), get()) }
}