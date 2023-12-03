package com.example.afyamkononi.util

import java.text.SimpleDateFormat
import java.util.*

fun String.formatPublishedAt(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = dateFormat.parse(this) ?: return "Invalid date"

    val currentTime = Date().time
    val timeDifference = currentTime - date.time
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24
    val weeksInMilli = daysInMilli * 7
    val monthsInMilli = daysInMilli * 30
    val yearsInMilli = monthsInMilli * 12

    return when {
        timeDifference < minutesInMilli -> "just now"
        timeDifference < 2 * minutesInMilli -> "a minute ago"
        timeDifference < hoursInMilli -> "${timeDifference / minutesInMilli} minutes ago"
        timeDifference < 2 * hoursInMilli -> "an hour ago"
        timeDifference < daysInMilli -> "${timeDifference / hoursInMilli} hours ago"
        timeDifference < 2 * daysInMilli -> "yesterday"
        timeDifference < weeksInMilli -> "${timeDifference / daysInMilli} days ago"
        timeDifference < 2 * weeksInMilli -> "last week"
        timeDifference < monthsInMilli -> "${timeDifference / weeksInMilli} weeks ago"
        timeDifference < 2 * monthsInMilli -> "last month"
        timeDifference < yearsInMilli -> "${timeDifference / monthsInMilli} months ago"
        else -> "${timeDifference / yearsInMilli} years ago"
    }
}
