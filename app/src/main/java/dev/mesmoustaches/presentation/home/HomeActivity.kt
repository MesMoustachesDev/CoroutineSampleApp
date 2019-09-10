package dev.mesmoustaches.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.nonNullObserve
import androidx.lifecycle.nonNullObserveConsume
import com.google.android.material.snackbar.Snackbar
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.linkVisibilityTo
import dev.mesmoustaches.presentation.details.EventDetailsActivity
import dev.mesmoustaches.presentation.routing.FilterScreen
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeActivityViewModel by viewModel()
    private val filterScreen by inject<FilterScreen>()

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter({
            viewModel.loadMore()
        }) {
            startActivity(EventDetailsActivity.createIntent(this, it.id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setObservers()
        setListeners()

        homeRecyclerView.adapter = homeAdapter
    }

    private fun setObservers() {
        loader.linkVisibilityTo(viewModel.loadingLiveData, this)

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
        refresh?.setOnRefreshListener {
            viewModel.refresh(forceUpdate = true)
            refresh?.isRefreshing = false
        }

        fab?.setOnClickListener {
            startActivity(filterScreen.getIntent())
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}