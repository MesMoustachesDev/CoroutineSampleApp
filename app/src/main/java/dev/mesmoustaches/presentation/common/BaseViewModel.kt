package dev.mesmoustaches.presentation.common

import android.content.Context
import androidx.lifecycle.ViewModel
import dev.mesmoustaches.domain.error.errorToFailure
import dev.mesmoustaches.domain.error.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(context: Context) : ViewModel(), CoroutineScope {
    protected var job = Job()
    val getError = { error: Exception -> error.errorToFailure().toMessage(context) }

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}