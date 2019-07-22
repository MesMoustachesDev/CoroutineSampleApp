package dev.mesmoustaches.presentation.di

import android.content.Intent
import dev.mesmoustaches.presentation.home.HomeActivity
import dev.mesmoustaches.presentation.home.HomeActivityViewModel
import dev.mesmoustaches.presentation.routing.HomeScreen
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModules = module {
    factory {
        object : HomeScreen {
            override fun getIntent(): Intent = HomeActivity.createIntent(get())
        }
    }
}

val viewModelModules = module {
    viewModel { HomeActivityViewModel(get(), get(), get()) }
}

val presentationModules = activityModules + viewModelModules