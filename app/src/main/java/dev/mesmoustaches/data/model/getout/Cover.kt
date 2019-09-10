package dev.mesmoustaches.data.model.getout


import com.google.gson.annotations.SerializedName

data class Cover(
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("path")
    val id: String?,
    @SerializedName("mimetype")
    val mimetype: String?,
    @SerializedName("width")
    val width: Int?
)