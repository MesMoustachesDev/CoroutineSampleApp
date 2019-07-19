package dev.mesmoustaches.domain.usecase

interface CoroutineInteractor<in I> {
    suspend fun execute(input: I? = null)
}