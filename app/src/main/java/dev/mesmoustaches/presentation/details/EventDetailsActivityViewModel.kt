package dev.mesmoustaches.presentation.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import dev.mesmoustaches.domain.usecase.GetEventDetailsUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.map

class EventDetailsActivityViewModel(
    private val eventsLiveDataUseCase: GetEventDetailsUseCase,
    context: Context
) : BaseViewModel(context) {

    val errorLiveData = MutableLiveData<String>()

    lateinit var imageLiveData: LiveData<String>
    lateinit var descriptionLiveData: LiveData<String>
    lateinit var titleLiveData: LiveData<String>
    lateinit var addressLiveData: LiveData<String>

    lateinit var facebookLiveData: LiveData<String>
    lateinit var phoneLiveData: LiveData<String>
    lateinit var mailLiveData: LiveData<String>

    private var coordinates: Pair<Double, Double>? = null

    val openMapLiveData = MutableLiveData<Pair<Double, Double>>()

    fun getLiveData(extractId: String?) {
        val liveData = eventsLiveDataUseCase.getStream(extractId)
        imageLiveData = liveData.map {
            coordinates = it.position
            it.image ?: ""
        }.asLiveData()
        descriptionLiveData = liveData.map {
            it.description
        }.asLiveData()
        titleLiveData = liveData.map {
            it.title
        }.asLiveData()
        addressLiveData = liveData.map {
            it.address ?: ""
        }.asLiveData()

        facebookLiveData = liveData.map {
            it.facebook ?: ""
        }.asLiveData()

        phoneLiveData = liveData.map {
            it.phone ?: ""
        }.asLiveData()

        mailLiveData = liveData.map {
            it.mail ?: ""
        }.asLiveData()
    }

    fun onMapClicked() {
        openMapLiveData.postValue(coordinates)
    }
}