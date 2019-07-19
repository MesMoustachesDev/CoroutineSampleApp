package dev.mesmoustaches

import android.app.Application
import com.facebook.stetho.Stetho
import dev.mesmoustaches.data.di.databaseModule
import dev.mesmoustaches.data.di.networkModules
import dev.mesmoustaches.data.di.repoModules
import dev.mesmoustaches.domain.di.useCaseModules
import dev.mesmoustaches.presentation.di.activityModules
import dev.mesmoustaches.presentation.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@MainApplication)
            // modules

            val appModules =
                networkModules + activityModules + viewModelModules + repoModules + useCaseModules + databaseModule
            modules(appModules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //Do something
        }

        Stetho.initializeWithDefaults(this)
    }
}