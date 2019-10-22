package dev.mesmoustaches.domain.model

data class FilterCategoryDomain(
    val id: String,
    val nameToDisplay: String,
    val filters: List<Filter>?
)

data class Filter(
    val name: String,
    val path: String,
    var selected: Boolean,
    val type: FilterType
)

sealed class FilterType {
    object ListFilter : FilterType()
    object CheckBoxFilter : FilterType()
}