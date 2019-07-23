package dev.mesmoustaches.data.events.remote

import dev.mesmoustaches.data.model.getout.GetOutParis
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getEvents(
        @Query("dataset") domain: String = "que-faire-a-paris-",
        @Query("sort") sort: String = "-date_end",
        @Query("facet") category: String = "category",
        @Query("facet") blind: String = "blind",
        @Query("facet") deaf: String = "deaf",
        @Query("facet") access_type: String = "access_type",
        @Query("facet") price_type: String = "price_type",
        @Query("rows") rows: Int = 50,
        @Query("start") start: Int = 0
    ): GetOutParis
}