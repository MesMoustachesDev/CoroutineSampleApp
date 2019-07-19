package dev.mesmoustaches.domain.usecase

import android.content.Context
import androidx.lifecycle.Transformations
import dev.mesmoustaches.data.repository.EmployeeRepository
import dev.mesmoustaches.domain.model.EmployeeDomain
import timber.log.Timber

class EmployeesLiveDataUseCase(
    private val employeesRepository: EmployeeRepository,
    private val context: Context
) : CoroutineUseCase<Void, List<EmployeeDomain>>() {
//    override fun getData(): Either<Failure, LiveData<List<EmployeeDomain>>> {
//        Transformation.map employeesRepository.getEmployees()
//    }

    val state = Transformations.map(employeesRepository.getEmployees()) { list ->
        list.map {
            EmployeeDomain(name = it.employeeName ?: "", picture = it.profileImage ?: "")
        }
    }
//        MediatorLiveData<LoadingState<List<EmployeeDomain>>>()

    override suspend fun createCoroutine(input: Void?) {
        try {
//            state.postValue(LoadingState.Loading())
//            state.addSource(employeesRepository.getEmployees()) { list ->
//                state.postValue(LoadingState.Loaded(list.map {
//                    EmployeeDomain(name = it.employeeName ?: "", picture = it.profileImage ?: "")
//                }))
//            }
            employeesRepository
                .fetchEmployees()
        } catch (e: Exception) {
            Timber.e(e, "Error getting employees")
            throw e
//            state.postValue(LoadingState.Error(e.errorToFailure().toMessage(context)))
        }
    }
}