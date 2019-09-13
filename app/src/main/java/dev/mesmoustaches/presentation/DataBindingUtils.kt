package dev.mesmoustaches.presentation

import android.content.Intent
import android.net.Uri
import android.view.setTextViewHTML
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView


@BindingAdapter("imageUrl")
fun AppCompatImageView.loadImage(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(context)
            .load(imageUrl)
            .into(this)
    }
}

@BindingAdapter("htmlText")
fun MaterialTextView.bindHtml(html: String?) {
    html?.let {
        this.setTextViewHTML(html) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            context.startActivity(browserIntent)
        }
    }
}