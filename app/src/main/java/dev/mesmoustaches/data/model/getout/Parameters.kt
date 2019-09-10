package dev.mesmoustaches.data.model.getout


import com.google.gson.annotations.SerializedName

data class Parameters(
    @SerializedName("dataset")
    val dataset: String?,
    @SerializedName("facet")
    val facet: List<String?>?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("rows")
    val rows: Int?,
    @SerializedName("sort")
    val sort: List<String?>?,
    @SerializedName("start")
    val start: Int?,
    @SerializedName("timezone")
    val timezone: String?
)



