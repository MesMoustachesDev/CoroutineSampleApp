package dev.mesmoustaches.presentation.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.launchDataLoad
import dev.mesmoustaches.domain.usecase.GetEventsUseCase
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.Job

class HomeActivityViewModel(
    private val eventsLiveDataUseCase: GetEventsUseCase,
    private val filterLiveDataUseCase: GetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {

    private var localJob: Job? = null
    private var oldSize = -1

    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val eventsLiveData =
        Transformations.map(eventsLiveDataUseCase.data) { list ->
            val arrayList: ArrayList<HomeAdapter.Cell> = ArrayList(list
                .sortedBy { it.timeStamp }
                .map { it.toCell() })
            if (oldSize != list.size) {
                oldSize = list.size
                arrayList.add(HomeAdapter.Cell.NeedMore)
            }
            arrayList
        }

    val filtersLiveData = filterLiveDataUseCase.data

    init {
        oldSize = -1
        refresh()
    }

    fun refresh(forceUpdate: Boolean = false) {
        localJob = launchDataLoad(
            loadingLiveData,
            errorLiveData,
            getError
        ) {
            eventsLiveDataUseCase.execute(GetEventsUseCase.Params(forceUpdate = forceUpdate))
        }
    }

    override fun onCleared() {
        super.onCleared()
        localJob?.cancel()
    }

    fun loadMore() {
        localJob = launchDataLoad(
            null,
            errorLiveData,
            getError
        ) {
            eventsLiveDataUseCase.execute(GetEventsUseCase.Params(loadMore = true))
        }
    }
}