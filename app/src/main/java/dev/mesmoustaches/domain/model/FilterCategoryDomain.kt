package dev.mesmoustaches.domain.model

import dev.mesmoustaches.R
import dev.mesmoustaches.data.model.getout.FacetGroup

data class FilterCategoryDomain(
    val name: String,
    val nameToDisplay: Int,
    val filters: List<Filter>?
)

data class Filter(
    val name: String,
    val path: String,
    val selected: Boolean,
    val type: FilterType
)

sealed class FilterType {
    object ListFilter : FilterType()
    object CheckBoxFilter : FilterType()
}

fun FacetGroup.toDomain(): FilterCategoryDomain {
    return FilterCategoryDomain(
        name = name,
        nameToDisplay = name.toDisplay(),
        filters = facets?.map {
            Filter(
                it?.name ?: "",
                it?.path ?: "",
                it?.selected ?: false,
                if (it?.path?.toIntOrNull() == null) FilterType.ListFilter else FilterType.CheckBoxFilter
            )
        }
    )
}

private fun String.toDisplay(): Int = when (this) {
    "price_type" -> R.string.price_type
    "access_type" -> R.string.access_type
    "deaf" -> R.string.deaf
    "blind" -> R.string.blind
    "pmr" -> R.string.pmr
    "category" -> R.string.category
    else -> R.string.unknown
}
