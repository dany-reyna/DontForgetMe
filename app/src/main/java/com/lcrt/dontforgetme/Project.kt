package com.lcrt.dontforgetme

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

internal const val PROJECT_NAME_LENGTH = 30
internal const val PROJECT_CLIENT_LENGTH = 30
internal const val PROJECT_DESC_LENGTH = 200

internal const val COLOR_PURPLE = "Morado"
internal const val COLOR_GREEN = "Verde"
internal const val COLOR_BLUE = "Azul"
internal const val COLOR_YELLOW = "Amarillo"
internal const val COLOR_BROWN = "Cafe"
internal const val COLOR_RED = "Rojo"
internal const val COLOR_ORANGE = "Naranja"

internal val sqliteDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
internal val simpleDateFormatter = SimpleDateFormat("dd/MM/yy", Locale.US)

@Parcelize
data class Project(
        val id: Int,
        val name: String,
        val color: String,
        val client: String,
        val description: String,
        val deadline: String
) : Parcelable {
    // String to display on spinner lists
    override fun toString(): String {
        return name
    }
}

internal fun getColorResourceId(color: String): Int {
    return when (color) {
        COLOR_PURPLE -> R.drawable.ic_project_bookmark_purple
        COLOR_GREEN -> R.drawable.ic_project_bookmark_green
        COLOR_BLUE -> R.drawable.ic_project_bookmark_blue
        COLOR_YELLOW -> R.drawable.ic_project_bookmark_yellow
        COLOR_BROWN -> R.drawable.ic_project_bookmark_brown
        COLOR_RED -> R.drawable.ic_project_bookmark_red
        COLOR_ORANGE -> R.drawable.ic_project_bookmark_orange
        else -> R.drawable.ic_project_bookmark_purple
    }
}