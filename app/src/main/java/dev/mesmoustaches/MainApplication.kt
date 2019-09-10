package dev.mesmoustaches

import android.app.Application
import com.facebook.stetho.Stetho
import dev.mesmoustaches.data.di.dataModules
import dev.mesmoustaches.domain.di.useCaseModules
import dev.mesmoustaches.presentation.di.presentationModules
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
                dataModules + presentationModules + useCaseModules
            modules(appModules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        } else {
            //Do something
        }

        Stetho.initializeWithDefaults(this)
    }
}