package dev.mesmoustaches.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.nonNullObserve
import dev.mesmoustaches.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import dev.mesmoustaches.presentation.filter.FilterFragment
import dev.mesmoustaches.presentation.home.HomeFragment


class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setObservers()
    }

    private fun setObservers() {
        nonNullObserve(viewModel.currentViewLiveData) {
            when (it) {
                is MainActivityViewModel.CurrentView.FilterView -> launchFiltersView()
                is MainActivityViewModel.CurrentView.HomeView -> launchHomeView()
            }
        }
    }

    private fun launchFiltersView() {
        val newFragment = FilterFragment()
        val transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_left)
        transaction.replace(R.id.mainContent, newFragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }

    private fun launchHomeView() {
        val newFragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.mainContent, newFragment)

        transaction.commit()
    }
}