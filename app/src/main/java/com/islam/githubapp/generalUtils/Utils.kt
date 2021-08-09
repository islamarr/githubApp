package com.islam.githubapp.generalUtils

import android.content.Context
import android.net.ConnectivityManager
import androidx.viewbinding.BuildConfig

object Utils {

    const val DEVURL = "https://api.github.com/"
    const val URL = "https://api.github.com/"

    fun getUrl(): String {
        return if (BuildConfig.DEBUG)
            DEVURL
        else
            URL
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}