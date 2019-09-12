package dev.mesmoustaches.presentation

import android.content.Intent
import android.net.Uri
import android.view.setTextViewHTML
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun AppCompatImageView.loadImage(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(context)
            .load(imageUrl)
            .into(this)
    }
}

@BindingAdapter("htmlText")
fun AppCompatTextView.bindHtml(html: String?) {
    html?.let {
        this.setTextViewHTML(html) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            context.startActivity(browserIntent)
        }
    }
}