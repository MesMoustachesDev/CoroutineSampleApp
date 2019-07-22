package dev.mesmoustaches.data.model.getout


import com.google.gson.annotations.SerializedName

data class Cover(
    @SerializedName("color_summary")
    val colorSummary: List<String?>?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("last_synchronized")
    val lastSynchronized: String?,
    @SerializedName("mimetype")
    val mimetype: String?,
    @SerializedName("thumbnail")
    val thumbnail: Boolean?,
    @SerializedName("width")
    val width: Int?
)