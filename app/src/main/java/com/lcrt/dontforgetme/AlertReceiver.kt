package com.lcrt.dontforgetme

// TODO cambiar package

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lcrt.dontforgetme.R

class AlertReceiver : BroadcastReceiver() {

    private lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            notificationHelper = NotificationHelper(it)
        }

        intent?.let {
            if (it.hasExtra(EXTRA_TASK_NOTIFICATION_ID)
                    && it.hasExtra(EXTRA_NOTIFICATION_TITLE)
                    && it.hasExtra(EXTRA_NOTIFICATION_MESSAGE)
                    && it.hasExtra(EXTRA_NOTIFICATION_ICON)) {

                val id = it.getIntExtra(EXTRA_TASK_NOTIFICATION_ID, 0)
                val title = it.getStringExtra(EXTRA_NOTIFICATION_TITLE)
                val message = it.getStringExtra(EXTRA_NOTIFICATION_MESSAGE)
                val icon = it.getIntExtra(EXTRA_NOTIFICATION_ICON, R.drawable.ic_priority_high)
                val notification = notificationHelper.getChannel1Notification(title, message, icon)
                notificationHelper.manager.notify(id, notification)
            }
        }
    }
}