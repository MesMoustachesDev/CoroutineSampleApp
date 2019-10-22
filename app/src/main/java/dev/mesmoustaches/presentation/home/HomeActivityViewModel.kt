package dev.mesmoustaches.presentation.home

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.launchDataLoad
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import dev.mesmoustaches.domain.usecase.GetEventsUseCase
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.domain.usecase.GetPaginationSizeUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import kotlinx.coroutines.Job

class HomeActivityViewModel(
    private val eventsLiveDataUseCase: GetEventsUseCase,
    filterLiveDataUseCase: GetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {

    private var localJob: Job? = null

    val loadingLiveData = eventsLiveDataUseCase.loading
    val errorLiveData = MutableLiveData<String>()
    val eventsLiveData =
        Transformations.map(eventsLiveDataUseCase.data) { list ->
            val arrayList: ArrayList<HomeAdapter.Cell> = ArrayList(list.events
                .map { it.toCell() })
            if (list.hasMore) {
                arrayList.add(HomeAdapter.Cell.NeedMore)
            }
            arrayList
        }

    val filtersLiveData = MediatorLiveData<List<FilterCategoryDomain>>()

    init {
        refresh()

        filtersLiveData.addSource(filterLiveDataUseCase.data) {
            filtersLiveData.postValue(it)
        }
    }

    private fun refresh(forceUpdate: Boolean = false) {
        localJob = launchDataLoad(
            null,
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