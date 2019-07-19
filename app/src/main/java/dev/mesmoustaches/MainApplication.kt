package dev.mesmoustaches

import android.app.Application
import com.facebook.stetho.Stetho
import dev.mesmoustaches.di.appModules
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