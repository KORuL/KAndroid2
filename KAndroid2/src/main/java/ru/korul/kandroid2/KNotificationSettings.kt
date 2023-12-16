package ru.korul.kandroid2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

fun Context.goToNotificationSettings(channel: String?) {
    val intent = Intent()
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (channel != null) {
                intent.action = Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel)
            } else {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            }
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.applicationContext!!.packageName)
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            if (channel != null) {
                intent.action = Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel)
            } else {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            }
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.applicationContext!!.packageName)
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1 -> {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.applicationContext!!.packageName)
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra("app_package", this.applicationContext!!.packageName)
            intent.putExtra("app_uid", this.applicationContext!!.applicationInfo.uid)
        }

        Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT -> {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:" + this.applicationContext!!.packageName)
        }
    }
    this.startActivity(intent)
}