package dev.mesmoustaches.domain.usecase

import androidx.lifecycle.Transformations
import dev.mesmoustaches.coroutines.CoroutineUseCase
import dev.mesmoustaches.data.repository.EmployeeRepository
import dev.mesmoustaches.domain.model.EmployeeDomain
import timber.log.Timber

class GetEmployeeUseCase(
    private val employeesRepository: EmployeeRepository
) : CoroutineUseCase<Boolean, List<EmployeeDomain>>() {

    val data = Transformations.map(employeesRepository.getEmployees()) { list ->
        list.map {
            EmployeeDomain(id = it.id, name = it.employeeName ?: "", picture = it.profileImage ?: "")
        }
    }

    override suspend fun createCoroutine(input: Boolean?) {
        try {
            employeesRepository
                .fetchEmployees(input ?: false)
        } catch (e: Exception) {
            Timber.e(e, "Error getting employees")
            throw e
        }
    }
}