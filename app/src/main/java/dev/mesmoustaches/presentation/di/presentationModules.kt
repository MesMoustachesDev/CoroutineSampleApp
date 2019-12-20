package dev.mesmoustaches.presentation.di

import android.content.Intent
import dev.mesmoustaches.presentation.MainActivityViewModel
import dev.mesmoustaches.presentation.details.EventDetailsActivityViewModel
import dev.mesmoustaches.presentation.filter.FilterFragment
import dev.mesmoustaches.presentation.filter.FilterFragmentViewModel
import dev.mesmoustaches.presentation.home.HomeFragment
import dev.mesmoustaches.presentation.home.HomeFragmentViewModel
import dev.mesmoustaches.presentation.routing.FilterScreen
import dev.mesmoustaches.presentation.routing.HomeScreen
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModules = module {
    factory {
        object : HomeScreen {
            override fun getIntent(args: Void?): Intent = HomeFragment.createIntent(get())
        } as HomeScreen
    }
    factory {
        object : FilterScreen {
            override fun getIntent(args: Void?): Intent = FilterFragment.createIntent(get())
        } as FilterScreen
    }
}

val viewModelModules = module {
    viewModel { HomeFragmentViewModel(get()) }
    viewModel { FilterFragmentViewModel(get(), get()) }
    viewModel { EventDetailsActivityViewModel(get(), get()) }
    viewModel { MainActivityViewModel(get(), get(), get(), get()) }
}

val presentationModules = activityModules + viewModelModules