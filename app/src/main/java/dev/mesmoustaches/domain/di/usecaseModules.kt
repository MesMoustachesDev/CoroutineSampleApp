package dev.mesmoustaches.domain.di

import dev.mesmoustaches.domain.usecase.*
import org.koin.dsl.module

val useCaseModules = module {
    factory { GetEventsUseCase(get()) }
    factory { GetFiltersUseCase(get()) }
    factory { SetFiltersUseCase(get()) }
    factory { GetEventDetailsUseCase(get()) }
    factory { GetPaginationSizeUseCase(get()) }
}