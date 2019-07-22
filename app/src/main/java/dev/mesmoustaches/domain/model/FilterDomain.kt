package dev.mesmoustaches.domain.model

import dev.mesmoustaches.data.model.getout.FacetGroup

data class FilterDomain(
    val name: String,
    val filters: List<Filter>?
)

data class Filter(
    val name: String,
    val path: String
)

fun FacetGroup.toDomain(): FilterDomain {

    return FilterDomain(
        name = name,
        filters = facets?.map { Filter(it?.name ?: "", it?.path ?: "")}
    )
}