package dev.mesmoustaches.data.utils

import dev.mesmoustaches.data.utils.Failure.FeatureFailure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    data class ServerError(val exception: Exception) : Failure()
    data class SimpleThrowable(
        val e: Throwable
    ) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}