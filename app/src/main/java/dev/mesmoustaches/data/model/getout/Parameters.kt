package dev.mesmoustaches.data.model.getout


import com.google.gson.annotations.SerializedName

data class Parameters(
    @SerializedName("dataset")
    val dataset: List<String?>?,
    @SerializedName("facet")
    val facet: List<String?>?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("rows")
    val rows: Int?,
    @SerializedName("timezone")
    val timezone: String?
)