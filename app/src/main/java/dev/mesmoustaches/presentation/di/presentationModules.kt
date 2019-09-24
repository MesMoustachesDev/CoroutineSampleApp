package dev.mesmoustaches.presentation.di

import android.content.Intent
import dev.mesmoustaches.presentation.details.EventDetailsActivityViewModel
import dev.mesmoustaches.presentation.filter.FilterActivity
import dev.mesmoustaches.presentation.filter.FilterActivityViewModel
import dev.mesmoustaches.presentation.home.HomeActivity
import dev.mesmoustaches.presentation.home.HomeActivityViewModel
import dev.mesmoustaches.presentation.routing.FilterScreen
import dev.mesmoustaches.presentation.routing.HomeScreen
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModules = module {
    factory {
        object : HomeScreen {
            override fun getIntent(args: Void?): Intent = HomeActivity.createIntent(get())
        } as HomeScreen
    }
    factory {
        object : FilterScreen {
            override fun getIntent(args: Void?): Intent = FilterActivity.createIntent(get())
        } as FilterScreen
    }
}

val viewModelModules = module {
    viewModel { HomeActivityViewModel(get(), get(), get(), get()) }
    viewModel { FilterActivityViewModel(get(), get(), get()) }
    viewModel { EventDetailsActivityViewModel(get(), get()) }
}

val presentationModules = activityModules + viewModelModules