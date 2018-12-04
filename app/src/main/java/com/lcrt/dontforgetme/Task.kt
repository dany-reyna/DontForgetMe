package com.lcrt.dontforgetme

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

internal const val TASK_NAME_LENGTH = 30
internal const val TASK_LOCATION_LENGTH = 50

internal const val PRIORITY_LOW = "Baja"
internal const val PRIORITY_MEDIUM = "Media"
internal const val PRIORITY_HIGH = "Alta"

internal val sqliteDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
internal val simpleDateTimeFormatter = SimpleDateFormat("dd/MM/yy, HH:mm", Locale.US)
internal val simpleTimeFormatter = SimpleDateFormat("HH:mm", Locale.US)

@Parcelize
data class Task(
        val id: Int,
        val name: String,
        val priority: String,
        val location: String,
        val startDateTime: String,
        val endDateTime: String,
        val notificationTime: String,
        val linkedProjectId: Int,
        val linkedProjectName: String,
        val linkedProjectColor: String
) : Parcelable

@Parcelize
data class TaskAux (
        val id: Int,
        val name: String,
        val priority: String,
        val location: String,
        val startDateTime: String,
        val endDateTime: String,
        val notificationTime: String,
        val linkedProjectId: Int
):Parcelable

internal fun getPriorityResourceId(priority: String): Int {
    return when (priority) {
        PRIORITY_LOW -> R.drawable.ic_priority_low
        PRIORITY_MEDIUM -> R.drawable.ic_priority_medium
        PRIORITY_HIGH -> R.drawable.ic_priority_high
        else -> R.drawable.ic_priority_low
    }
}