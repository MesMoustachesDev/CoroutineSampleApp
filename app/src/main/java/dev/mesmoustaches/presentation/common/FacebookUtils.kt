package dev.mesmoustaches.presentation.common

import android.content.Context
import android.content.pm.PackageManager


var FACEBOOK_URL = "https://www.facebook.com/"

//method to get the right URL to use in the intent
fun Context.getFacebookPageURL(id: String): String {
    return try {
        val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
        if (versionCode >= 3002850) { //newer versions of fb app
            "fb://facewebmodal/f?href=$FACEBOOK_URL$id"
        } else { //older versions of fb app
            "fb://page/$id"
        }
    } catch (e: PackageManager.NameNotFoundException) {
        FACEBOOK_URL //normal web url
    }
}