package dev.mesmoustaches.presentation.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.nonNullObserve
import androidx.lifecycle.nonNullObserveConsume
import com.google.android.material.snackbar.Snackbar
import dev.mesmoustaches.R
import dev.mesmoustaches.android.view.visible
import dev.mesmoustaches.databinding.ActivityEventDetailsBinding
import dev.mesmoustaches.presentation.common.getFacebookPageURL
import kotlinx.android.synthetic.main.activity_event_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class EventDetailsActivity : AppCompatActivity() {
    private val viewModel: EventDetailsActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        val binding: ActivityEventDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_event_details)
        binding.event = viewModel
        binding.lifecycleOwner = this

        setObservers()
    }

    private fun setObservers() {
        viewModel.getLiveData(extractId(intent))

        nonNullObserve(viewModel.facebookLiveData) { facebook ->
            facebookImage.visible()
            facebookImage.setOnClickListener {
                val facebookIntent = Intent(Intent.ACTION_VIEW)

                val facebookUrl = Uri.parse(facebook) //getFacebookPageURL(facebook.split("/").last())
                facebookUrl.pathSegments?.let {
                    facebookIntent.data = Uri.parse(getFacebookPageURL(it.last()))
                    startActivity(facebookIntent)
                }
            }
        }

        nonNullObserve(viewModel.mailLiveData) { mail ->
            mailImage.visible()
            mailImage.setOnClickListener {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "message/rfc822"
                i.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
                try {
                    startActivity(Intent.createChooser(i, getString(R.string.send_email)))
                } catch (ex: android.content.ActivityNotFoundException) {
                    Toast.makeText(
                        this@EventDetailsActivity,
                        "There are no email clients installed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        nonNullObserve(viewModel.phoneLiveData) { number ->
            phoneImage.visible()
            phoneImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$number")
                startActivity(intent)
            }
        }

        nonNullObserveConsume(viewModel.errorLiveData) {
            Timber.e("Error: $it")
            Snackbar.make(root, it, Snackbar.LENGTH_SHORT).show()
        }

        nonNullObserveConsume(viewModel.openMapLiveData) { coordinates: Pair<Double, Double> ->
            onMap(coordinates)
        }
    }

    private fun onMap(coordinates: Pair<Double?, Double?>?) {
        coordinates?.let {
            val gmmIntentUri =
                Uri.parse("http://maps.google.com/maps?q=loc:${coordinates.first},${coordinates.second}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
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