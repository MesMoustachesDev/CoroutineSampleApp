package dev.mesmoustaches.presentation.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.mesmoustaches.domain.usecase.EmployeesLiveDataUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel

class HomeActivityViewModel(
    private val employeesLiveDataUseCase: EmployeesLiveDataUseCase,
    context: Context
) : BaseViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val employeesLiveData =
        Transformations.map(employeesLiveDataUseCase.state) { list ->
            list.map { HomeAdapter.Cell(employeeName = it.name, image = it.picture) }
        }

    init {
        launchDataLoad(loadingLiveData, errorLiveData, context) {
            employeesLiveDataUseCase.execute()
        }
    }
}