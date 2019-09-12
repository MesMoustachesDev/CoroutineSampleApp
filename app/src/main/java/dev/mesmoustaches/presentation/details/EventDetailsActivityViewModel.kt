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

    val errorLiveData = MutableLiveData<String>()

    lateinit var imageLiveData: LiveData<String>
    lateinit var descriptionLiveData: LiveData<String>
    private var coordinates: Pair<Double, Double>? = null

    val openMapLiveData = MutableLiveData<Pair<Double, Double>>()

    fun getLiveData(extractId: String?) {
        val liveData = eventsLiveDataUseCase.getLiveData(extractId)
        imageLiveData = Transformations.map(liveData) {
            coordinates = it.position
            it.image
        }
        descriptionLiveData = Transformations.map(liveData) {
            it.description
        }
    }

    fun onMapClicked() {
        openMapLiveData.postValue(coordinates)
    }
}