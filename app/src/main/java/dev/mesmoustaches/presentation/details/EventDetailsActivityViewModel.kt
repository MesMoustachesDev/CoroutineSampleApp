package dev.mesmoustaches.presentation.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import dev.mesmoustaches.domain.usecase.GetEventDetailsUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel

class EventDetailsActivityViewModel(
    private val eventsLiveDataUseCase: GetEventDetailsUseCase,
    context: Context
) : BaseViewModel(context) {

    val loadingLiveData = eventsLiveDataUseCase.loading
    val errorLiveData = MutableLiveData<String>()
    var imageLiveData: LiveData<String?> ?= null

    fun getLiveData(extractId: String?) {
        val liveData = eventsLiveDataUseCase.getLiveData(extractId)
        imageLiveData = Transformations.map(liveData) {
            it.image
        }
    }
}