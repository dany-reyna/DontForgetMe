package com.lcrt.dontforgetme

// TODO cambiar package

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.support.v4.app.NotificationCompat

internal const val EXTRA_TASK_NOTIFICATION_ID = "com.lcrt.dontforgetme.TASK_NOTIFICATION_ID"
internal const val EXTRA_NOTIFICATION_TITLE = "com.lcrt.dontforgetme.NOTIFICATION_TITLE"
internal const val EXTRA_NOTIFICATION_MESSAGE = "com.lcrt.dontforgetme.NOTIFICATION_MESSAGE"
internal const val EXTRA_NOTIFICATION_ICON = "com.lcrt.dontforgetme.NOTIFICATION_ICON"

private const val CHANNEL_1_ID = "channel1"

class NotificationHelper(base: Context) : ContextWrapper(base) {
    internal lateinit var manager: NotificationManager

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH)
            channel1.description = "This is Channel 1"

            manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
        }
    }

    fun getChannel1Notification(title: String, message: String, icon: Int): Notification {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_1_ID).apply {
            setContentTitle(title)
            setContentText(message)
            setSmallIcon(icon)
        }
        return builder.build()
    }
}