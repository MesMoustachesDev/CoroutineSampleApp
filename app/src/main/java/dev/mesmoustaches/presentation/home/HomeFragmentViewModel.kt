package dev.mesmoustaches.presentation.home

import android.content.Context
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.Job

class HomeFragmentViewModel(
    context: Context
) : BaseViewModel(context) {

    private var localJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        localJob?.cancel()
    }
}