package dev.mesmoustaches.domain.di

import dev.mesmoustaches.domain.usecase.GetEmployeeUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory { GetEmployeeUseCase(get()) }
}