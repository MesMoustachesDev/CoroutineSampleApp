package dev.mesmoustaches.domain.di

import dev.mesmoustaches.domain.usecase.GetEventsUseCase
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory { GetEventsUseCase(get()) }
    factory { GetFiltersUseCase(get()) }
}