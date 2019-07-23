package dev.mesmoustaches.presentation.filter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel

class FilterActivityViewModel(
    filterLiveDataUseCase: GetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {

    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val filtersLiveData =
        Transformations.map(filterLiveDataUseCase.data) { list ->
            list.map { it.toCell() }
        }
}