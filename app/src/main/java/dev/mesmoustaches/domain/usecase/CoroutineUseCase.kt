package dev.mesmoustaches.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in REQUEST, RESPONSE> : CoroutineInteractor<REQUEST> {

    abstract suspend fun createCoroutine(input: REQUEST?)

    override suspend fun execute(input: REQUEST?) =
        withContext(Dispatchers.IO) { createCoroutine(input) }
}