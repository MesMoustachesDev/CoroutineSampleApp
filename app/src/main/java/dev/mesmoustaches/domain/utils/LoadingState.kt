package dev.mesmoustaches.domain.utils

sealed class LoadingState<T> {
    data class Loaded<T>(val data: T): LoadingState<T>()
    class Loading<T>: LoadingState<T>()
    data class Error<T>(val error: String): LoadingState<T>()
}