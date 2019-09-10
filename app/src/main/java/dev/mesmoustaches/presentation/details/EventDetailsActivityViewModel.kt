package dev.mesmoustaches.presentation.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import dev.mesmoustaches.domain.usecase.GetEventDetailsUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel

class EventDetailsActivityViewModel(
    private val eventsLiveDataUseCase: GetEventDetailsUseCase,
    context: Context
) : BaseViewModel(context) {

    val loadingLiveData = eventsLiveDataUseCase.loading
    val errorLiveData = MutableLiveData<String>()

    fun getLiveData(extractId: String?) = eventsLiveDataUseCase.getLiveData(extractId)
}