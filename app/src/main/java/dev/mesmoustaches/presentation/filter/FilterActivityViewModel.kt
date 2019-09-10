package dev.mesmoustaches.presentation.filter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.launchDataLoad
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.domain.usecase.SetFiltersUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.Job

class FilterActivityViewModel(
    filterLiveDataUseCase: GetFiltersUseCase,
    private val setFilterLiveDataUseCase: SetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {
    private var localJob: Job? = null

    val loadingLiveData = filterLiveDataUseCase.loading
    val errorLiveData = MutableLiveData<String>()
    val filtersLiveData =
        Transformations.map(filterLiveDataUseCase.data) { list ->
            list.map { it.toCell() }
        }

    fun updateFilters(filters: List<FilterGroupAdapter.Cell>) {
        localJob?.cancel()
        localJob = launchDataLoad(
            null,
            errorLiveData,
            getError
        ) {
            setFilterLiveDataUseCase.execute(filters.map {
                FilterCategoryDomain(
                    id = it.id,
                    nameToDisplay = it.name,
                    filters = it.filters
                )
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        localJob?.cancel()
    }
}