package dev.mesmoustaches.domain.error

import android.content.Context
import dev.mesmoustaches.R
import retrofit2.HttpException
import java.net.UnknownHostException

fun Throwable.errorToFailure(): Failure {
    return when (this) {
        is HttpException -> Failure.ServerError(this)
        is UnknownHostException -> Failure.NetworkConnection
        else -> Failure.SimpleThrowable(this)
    }
}

fun Failure.toMessage(context: Context): String {
    return when (this) {
        is Failure.ServerError -> this.exception.message
            ?: context.getString(R.string.generic_error_server)
        Failure.NetworkConnection -> context.getString(R.string.generic_error_noInternet)
        is Failure.SimpleThrowable -> this.e.message
            ?: context.getString(R.string.generic_empty_message)
        else -> context.getString(R.string.generic_error_unhandled)
    }
}

//fun Exception.extractServerError(): Failure.ServerError? {
//    val errorBody = (this as? HttpException)?.response()?.errorBody()?.string()
//    return try {
//        Gson().fromJson(errorBody, Failure.ServerError::class.java)
//    } catch (e: Exception) {
//        return try {
//            val serverErrorWithoutResponse =
//                Gson().fromJson(errorBody, ServerErrorWithoutResponse::class.java)
//            Failure.ServerError(
//                message = serverErrorWithoutResponse?.message,
//                status = serverErrorWithoutResponse?.status
//            )
//        } catch (e: Exception) {
//            null
//        }
//    }