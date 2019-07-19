package dev.mesmoustaches.presentation.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.launchDataLoad
import dev.mesmoustaches.domain.usecase.EmployeesLiveDataUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel

class HomeActivityViewModel(
    private val employeesLiveDataUseCase: EmployeesLiveDataUseCase,
    context: Context
) : BaseViewModel(context) {

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
        launchDataLoad(
            loadingLiveData,
            errorLiveData,
            getError
        ) {
            employeesLiveDataUseCase.execute(forceUpdate)
        }
    }
}