package dev.mesmoustaches.presentation.filter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.mesmoustaches.R
import dev.mesmoustaches.domain.model.Filter
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.domain.usecase.SetFiltersUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kot.capitalizeEachWord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FilterFragmentViewModel(
    filterLiveDataUseCase: GetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {

    val errorLiveData = MutableLiveData<String>()
    val filtersLiveData =
        Transformations.map(filterLiveDataUseCase.data) { list ->
            list.map { filterCategoryDomain ->
                filterCategoryDomain.toCell(
                    filterCategoryDomain.nameToDisplay.toDisplay(),
                    filterCategoryDomain.filters?.map { it.toFilterCell() })
            }
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

    private fun Filter.toFilterCell(): FilterAdapter.FilterCell =
        FilterAdapter.FilterCell(
            name = when (this.name) {
                "0" -> "Non"
                "1" -> "Oui"
                else -> this.name.capitalizeEachWord()
            },
            path = this.path,
            selected = this.selected,
            type = this.type
        )
}