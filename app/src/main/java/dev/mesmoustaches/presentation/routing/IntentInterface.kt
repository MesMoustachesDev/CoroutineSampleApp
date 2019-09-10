package dev.mesmoustaches.presentation.routing

import android.content.Intent

interface IntentInterface {
    fun getIntent(args: Void? = null): Intent
}