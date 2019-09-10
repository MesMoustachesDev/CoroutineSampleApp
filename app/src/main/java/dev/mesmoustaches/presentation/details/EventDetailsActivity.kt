package dev.mesmoustaches.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.nonNullObserve
import androidx.lifecycle.nonNullObserveConsume
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dev.mesmoustaches.R
import dev.mesmoustaches.domain.model.EventDomain
import kotlinx.android.synthetic.main.activity_event_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class EventDetailsActivity : AppCompatActivity() {
    private val viewModel: EventDetailsActivityViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        setObservers()
    }

    private fun setObservers() {

        nonNullObserveConsume(viewModel.errorLiveData) {
            Timber.e("Error: $it")
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }

        nonNullObserve(viewModel.getLiveData(extractId(intent))) { event: EventDomain ->
            Glide.with(this)
                .load(event.image)
                .placeholder(R.drawable.logo)
                .into(eventDetailsMainImage)
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