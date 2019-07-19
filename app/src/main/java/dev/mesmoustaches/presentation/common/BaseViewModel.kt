package dev.mesmoustaches.presentation.common

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.mesmoustaches.domain.error.errorToFailure
import dev.mesmoustaches.domain.error.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun launchDataLoad(
        loadingLiveData: MutableLiveData<Boolean> ?= null,
        errorLiveData: MutableLiveData<String> ?= null,
        context: Context,
        block: suspend () -> Unit
    ): Job {
        return viewModelScope.launch {
            try {
                loadingLiveData?.value = true
                block()
            } catch (error: Exception) {
                errorLiveData?.value = error.errorToFailure().toMessage(context)
            } finally {
                loadingLiveData?.value = false
            }
        }
    }
}