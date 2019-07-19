package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.coroutines.CoroutineUseCase
import dev.mesmoustaches.data.repository.EmployeeRepository
import dev.mesmoustaches.domain.model.EmployeeDomain
import timber.log.Timber

class EmployeesLiveDataUseCase(
    private val employeesRepository: EmployeeRepository
) : CoroutineUseCase<Void, List<EmployeeDomain>>() {

    val data = Transformations.map(employeesRepository.getEmployees()) { list ->
        list.map {
            EmployeeDomain(name = it.employeeName ?: "", picture = it.profileImage ?: "")
        }
    }

    override suspend fun createCoroutine(input: Void?) {
        try {
            employeesRepository
                .fetchEmployees()
        } catch (e: Exception) {
            Timber.e(e, "Error getting employees")
            throw e
        }
    }
}