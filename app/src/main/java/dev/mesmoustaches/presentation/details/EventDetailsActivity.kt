package dev.mesmoustaches.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.nonNullObserveConsume
import com.google.android.material.snackbar.Snackbar
import dev.mesmoustaches.R
import dev.mesmoustaches.databinding.ActivityEventDetailsBinding
import kotlinx.android.synthetic.main.activity_event_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class EventDetailsActivity : AppCompatActivity() {
    private val viewModel: EventDetailsActivityViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        val binding: ActivityEventDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_details)
        binding.event = viewModel
        binding.lifecycleOwner = this
        setObservers()
    }

    private fun setObservers() {
        viewModel.getLiveData(extractId(intent))

        nonNullObserveConsume(viewModel.errorLiveData) {
            Timber.e("Error: $it")
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {

        private const val EXTRA_ID: String = "EVENT_ID"

        fun createIntent(context: Context, id: String): Intent {
            return Intent(context, EventDetailsActivity::class.java)
                .apply {
                    putExtra(EXTRA_ID, id)
                }
        }

        fun extractId(intent: Intent): String? = intent.getStringExtra(EXTRA_ID)
    }
}