package dev.mesmoustaches.presentation

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.launchDataLoad
import dev.mesmoustaches.domain.model.Filter
import dev.mesmoustaches.domain.model.FilterCategoryDomain
import dev.mesmoustaches.domain.usecase.GetEventsUseCase
import dev.mesmoustaches.domain.usecase.GetFiltersUseCase
import dev.mesmoustaches.domain.usecase.SetFiltersUseCase
import dev.mesmoustaches.presentation.common.BaseViewModel
import dev.mesmoustaches.presentation.filter.FilterAdapter
import dev.mesmoustaches.presentation.filter.FilterGroupAdapter
import dev.mesmoustaches.presentation.home.HomeAdapter
import dev.mesmoustaches.presentation.home.toCell
import kotlinx.coroutines.*

class MainActivityViewModel(
    private val eventsLiveDataUseCase: GetEventsUseCase,
    filterLiveDataUseCase: GetFiltersUseCase,
    private val setFilterLiveDataUseCase: SetFiltersUseCase,
    context: Context
) : BaseViewModel(context) {

    private var localJob: Job? = null
    private val viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val currentViewLiveData = MutableLiveData<CurrentView>()

    val loadingLiveData = MutableLiveData<Boolean>()
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

        currentViewLiveData.postValue(CurrentView.HomeView)
    }

    fun onFilterClicked() {
        currentViewLiveData.postValue(CurrentView.FilterView)
    }

    private fun refresh(forceUpdate: Boolean = false) {
        localJob = launchDataLoad(
            loadingLiveData,
            errorLiveData,
            getError
        ) {
            eventsLiveDataUseCase.execute(GetEventsUseCase.Params(forceUpdate = forceUpdate))
        }
    }

    fun updateFilters(filters: List<FilterGroupAdapter.Cell>) {
        uiScope.launch {
            loadingLiveData.postValue(true)
            try {
                setFilterLiveDataUseCase.execute(filters.map { cell ->
                    FilterCategoryDomain(
                        id = cell.id,
                        nameToDisplay = cell.id,
                        filters = cell.filters?.map { it.toFilter() }
                    )
                })
            } catch (error: Exception) {
                loadingLiveData.postValue(false)
//                    errorLiveData?.value = getErrorMessage.invoke(error)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        localJob?.cancel()
    }

    fun loadMore() {
        localJob = launchDataLoad(
            loadingLiveData,
            errorLiveData,
            getError
        ) {
            eventsLiveDataUseCase.execute(GetEventsUseCase.Params(loadMore = true))
        }
    }


    private fun FilterAdapter.FilterCell.toFilter(): Filter =
        Filter(
            name = when (this.name) {
                "Non" -> "0"
                "Oui" -> "1"
                else -> this.name.decapitalize()
            },
            path = this.path,
            selected = this.selected,
            type = this.type
        )

    sealed class CurrentView {
        object HomeView : CurrentView()
        object FilterView : CurrentView()
    }
}