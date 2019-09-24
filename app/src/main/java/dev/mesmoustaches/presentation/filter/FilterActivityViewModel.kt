package dev.mesmoustaches.presentation.filter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.domain.usecase.SetFiltersUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FilterActivityViewModel(
    filterLiveDataUseCase: GetFiltersUseCase,
    private val setFilterLiveDataUseCase: SetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val loadingLiveData = filterLiveDataUseCase.loading
    val errorLiveData = MutableLiveData<String>()
    val filtersLiveData =
        Transformations.map(filterLiveDataUseCase.data) { list ->
            list.map { it.toCell() }
        }

    fun updateFilters(filters: List<FilterGroupAdapter.Cell>) {
            uiScope.launch {
                try {
                    setFilterLiveDataUseCase.execute(filters.map {
                        FilterCategoryDomain(
                            id = it.id,
                            nameToDisplay = it.name,
                            filters = it.filters
                        )
                    })
                } catch (error: Exception) {
//                    errorLiveData?.value = getErrorMessage.invoke(error)
                }
            }
    }
}