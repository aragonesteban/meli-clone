package com.meli.shared.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

fun Context.showToastMessage(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}

fun Context.isOnline(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < 23) {
        connectivityManager.activeNetworkInfo?.let { return it.isConnected }
    } else {
        connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)?.let {
                return it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }
    }

    return false
}