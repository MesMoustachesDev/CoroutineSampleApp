package dev.mesmoustaches.data.di

import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.mesmoustaches.BuildConfig
import dev.mesmoustaches.data.common.CacheStrategy
import dev.mesmoustaches.data.events.remote.ApiService
import dev.mesmoustaches.data.events.repository.EventRepository
import dev.mesmoustaches.data.events.repository.EventRepositoryImpl
import dev.mesmoustaches.data.events.repository.cache.EventCacheStrategy
import dev.mesmoustaches.data.model.getout.RecordData
import dev.mesmoustaches.data.room.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repoModules = module {
    single { EventRepositoryImpl(get(), get(), get(), get()) as EventRepository }
}

val cacheModules = module {
    single { EventCacheStrategy(get()) as CacheStrategy<RecordData> }
}

val netModule = module {
    single { StethoInterceptor() }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    single(named(NETWORK_API)) {

        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(get<StethoInterceptor>())
                    .addNetworkInterceptor(get<HttpLoggingInterceptor>())
                    .build()
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()
    }
    single { get<Retrofit>(named(NETWORK_API)).create(ApiService::class.java) as ApiService }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), DataBase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<DataBase>().eventDao() }
    single { get<DataBase>().filterDao() }

    single { RoomEventsDatabase(get()) as EventsDataSource }
    single { RoomFilterGroupsDatabase(get()) as FiltersDataSource}
}

val dataModules = netModule + databaseModule + cacheModules + repoModules

const val NETWORK_API = "NETWORK_API"