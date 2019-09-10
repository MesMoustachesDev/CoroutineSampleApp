package dev.mesmoustaches.data.model.getout


import com.google.gson.annotations.SerializedName

data class GetOutParis(
    @SerializedName("facet_groups")
    val facetGroups: List<FacetGroup?>?,
    @SerializedName("nhits")
    val nhits: Int?,
    @SerializedName("parameters")
    val parameters: Parameters?,
    @SerializedName("records")
    val records: List<RecordData?>?
)
