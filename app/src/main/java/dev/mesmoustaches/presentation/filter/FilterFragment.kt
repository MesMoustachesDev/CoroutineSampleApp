package dev.mesmoustaches.presentation.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.nonNullObserve
import androidx.lifecycle.nonNullObserveConsume
import com.google.android.material.snackbar.Snackbar
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.linkVisibilityTo
import dev.mesmoustaches.presentation.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_filter.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FilterFragment : Fragment() {
    private val parentViewModel: MainActivityViewModel by sharedViewModel()
    private val viewModel: FilterFragmentViewModel by viewModel()
    private val filterAdapter: FilterGroupAdapter by lazy {
        FilterGroupAdapter{
            Timber.d("onChanged: $it")
            parentViewModel.updateFilters(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.fragment_filter, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        filterRecyclerView.adapter = filterAdapter
    }

    private fun setObservers() {
        loader.linkVisibilityTo(parentViewModel.loadingLiveData, this)

        nonNullObserveConsume(viewModel.errorLiveData) {
            Timber.e("Error: $it")
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }
        nonNullObserve(viewModel.filtersLiveData) {
            if (it.isNotEmpty()) {
                filterAdapter.update(it)
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FilterFragment::class.java)
        }
    }
}