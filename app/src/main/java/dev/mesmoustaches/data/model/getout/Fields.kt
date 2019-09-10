package dev.mesmoustaches.data.model.getout


import com.google.gson.annotations.SerializedName

data class Fields(
    @SerializedName("access_type")
    val accessType: String?,
    @SerializedName("address_city")
    val addressCity: String?,
    @SerializedName("address_name")
    val addressName: String?,
    @SerializedName("address_street")
    val addressStreet: String?,
    @SerializedName("address_zipcode")
    val addressZipcode: String?,
    @SerializedName("blind")
    val blind: Int?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("contact_mail")
    val contactMail: String?,
    @SerializedName("contact_name")
    val contactName: String?,
    @SerializedName("contact_url")
    val contactUrl: String?,
    @SerializedName("cover")
    val cover: Cover?,
    @SerializedName("cover_alt")
    val coverAlt: String?,
    @SerializedName("cover_credit")
    val coverCredit: String?,
    @SerializedName("cover_url")
    val coverUrl: String?,
    @SerializedName("date_description")
    val dateDescription: String?,
    @SerializedName("date_end")
    val dateEnd: String?,
    @SerializedName("date_start")
    val dateStart: String?,
    @SerializedName("deaf")
    val deaf: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("path")
    val id: String?,
    @SerializedName("lat_lon")
    val latLon: List<Double?>?,
    @SerializedName("lead_text")
    val leadText: String?,
    @SerializedName("occurrences")
    val occurrences: String?,
    @SerializedName("pmr")
    val pmr: Int?,
    @SerializedName("price_detail")
    val priceDetail: String?,
    @SerializedName("price_type")
    val priceType: String?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("transport")
    val transport: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("url")
    val url: String?
)