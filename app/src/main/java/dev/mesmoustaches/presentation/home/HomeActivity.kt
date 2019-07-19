package dev.mesmoustaches.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.nonNullObserve
import com.google.android.material.snackbar.Snackbar
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.show
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeActivityViewModel by viewModel()
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        nonNullObserve(viewModel.loadingLiveData) {
            Timber.d("Loading: $it")
            loader.show(it)
        }

        nonNullObserve(viewModel.errorLiveData) {
            Timber.e("Error: $it")
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }

        nonNullObserve(viewModel.employeesLiveData)
        {
            homeAdapter.update(it)
        }

        homeRecyclerView.adapter = homeAdapter
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}