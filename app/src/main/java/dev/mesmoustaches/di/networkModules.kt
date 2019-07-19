package dev.mesmoustaches.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.mesmoustaches.BuildConfig
import dev.mesmoustaches.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

val networkModules = netModule

const val NETWORK_API = "NETWORK_API"
