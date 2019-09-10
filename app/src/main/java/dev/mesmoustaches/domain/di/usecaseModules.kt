package dev.mesmoustaches.domain.di

import dev.mesmoustaches.domain.usecase.GetEventDetailsUseCase
import dev.mesmoustaches.domain.usecase.GetEventsUseCase
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.domain.usecase.SetFiltersUseCase
import org.koin.dsl.module

val useCaseModules = module {
    factory { GetEventsUseCase(get()) }
    factory { GetFiltersUseCase(get()) }
    factory { SetFiltersUseCase(get()) }
    factory { GetEventDetailsUseCase(get()) }
}