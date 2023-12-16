package ru.korul.kandroid2

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

@Suppress("DEPRECATION")
fun Context.hasConnection(): Boolean {
    val cm = this.connectivityManager
    var wifiInfo = cm?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (wifiInfo != null && wifiInfo.isConnected) {
        return true
    }
    wifiInfo = cm?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    if (wifiInfo != null && wifiInfo.isConnected) {
        return true
    }
    wifiInfo = cm?.activeNetworkInfo
    return !(wifiInfo == null || !wifiInfo.isConnected)
}

@Suppress("DEPRECATION")
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.connectivityManager
    val activeNetworkInfo = connectivityManager?.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

@Suppress("DEPRECATION")
fun Context.isNetworkConnected(): Boolean {
    val cm = connectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val capability = cm?.getNetworkCapabilities(cm.activeNetwork)
        capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    } else {
        cm?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}