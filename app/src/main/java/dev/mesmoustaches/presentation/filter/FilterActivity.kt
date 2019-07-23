package dev.mesmoustaches.presentation.filter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.nonNullObserve
import androidx.lifecycle.nonNullObserveConsume
import com.google.android.material.snackbar.Snackbar
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.linkVisibilityTo
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.activity_home.loader
import kotlinx.android.synthetic.main.activity_home.root
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FilterActivity : AppCompatActivity() {
    private val viewModel: FilterActivityViewModel by viewModel()
    private val filterAdapter: FilterGroupAdapter by lazy {
        FilterGroupAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        setObservers()

        filterRecyclerView.adapter = filterAdapter
    }

    private fun setObservers() {
        loader.linkVisibilityTo(viewModel.loadingLiveData, this)

        nonNullObserveConsume(viewModel.errorLiveData) {
            Timber.e("Error: $it")
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }
        nonNullObserve(viewModel.filtersLiveData) {
            Timber.e("$it")
            filterAdapter.update(it)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, FilterActivity::class.java)
        }
    }
}