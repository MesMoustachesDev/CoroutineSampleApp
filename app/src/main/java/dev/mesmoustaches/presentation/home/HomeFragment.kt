package dev.mesmoustaches.presentation.home

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
import dev.mesmoustaches.presentation.details.EventDetailsActivity
import dev.mesmoustaches.presentation.routing.FilterScreen
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class HomeFragment : Fragment() {
    private val viewModel: MainActivityViewModel by sharedViewModel()
    private val filterScreen by inject<FilterScreen>()

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter({
            viewModel.loadMore()
        }) { cell ->
            context?.let {
                startActivity(EventDetailsActivity.createIntent(it, cell.id))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setListeners()

        homeRecyclerView.adapter = homeAdapter
    }

    private fun setObservers() {
        loader?.linkVisibilityTo(viewModel.loadingLiveData, this)

        nonNullObserveConsume(viewModel.errorLiveData) {
            Timber.e("Error: $it")
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }

        nonNullObserve(viewModel.eventsLiveData) { list ->
            if (true || homeAdapter.items.isEmpty()) {
                homeAdapter.update(list)
            } else {
                val snack = Snackbar.make(root, R.string.new_content, Snackbar.LENGTH_INDEFINITE)
                snack.setAction(R.string.accept) {
                    homeAdapter.update(list)
                }
                snack.show()
            }
        }

        nonNullObserve(viewModel.filtersLiveData) {
            Timber.e("$it")
        }
    }

    private fun setListeners() {
        fab?.setOnClickListener {
            viewModel.onFilterClicked()
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, HomeFragment::class.java)
        }
    }
}