package dev.mesmoustaches.presentation.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.launchDataLoad
import dev.mesmoustaches.domain.usecase.GetEmployeeUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.Job

class HomeActivityViewModel(
    private val employeesLiveDataUseCase: GetEmployeeUseCase,
    context: Context
) : BaseViewModel(context) {

    private var localJob: Job? = null

    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val employeesLiveData =
        Transformations.map(employeesLiveDataUseCase.data) { list ->
            list.map {
                HomeAdapter.Cell(
                    id = it.id,
                    employeeName = it.name,
                    image = it.picture
                )
            }
        }

    init {
        refresh()
    }

    fun refresh(forceUpdate: Boolean = false) {
        localJob = launchDataLoad(
            loadingLiveData,
            errorLiveData,
            getError
        ) {
            employeesLiveDataUseCase.execute(forceUpdate)
        }
    }

    override fun onCleared() {
        super.onCleared()
        localJob?.cancel()
    }
}