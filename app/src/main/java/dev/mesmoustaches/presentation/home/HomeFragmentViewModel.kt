package dev.mesmoustaches.presentation.home

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.Job

class HomeFragmentViewModel(
    filterLiveDataUseCase: GetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {

    private var localJob: Job? = null

    private val filtersLiveData = MediatorLiveData<List<FilterCategoryDomain>>()

    init {
        filtersLiveData.addSource(filterLiveDataUseCase.data) {
            filtersLiveData.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        localJob?.cancel()
    }
}