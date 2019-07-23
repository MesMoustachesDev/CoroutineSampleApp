package dev.mesmoustaches.data.model.getout


import com.google.gson.annotations.SerializedName

data class Facet(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("facets")
    val facets: List<Facet?>?,
    val selected: Boolean = false
)