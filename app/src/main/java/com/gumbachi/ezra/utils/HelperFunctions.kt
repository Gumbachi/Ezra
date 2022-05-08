package com.gumbachi.ezra.utils

fun convertMinutesToRuntime(totalMinutes: Int?): String {
    if (totalMinutes == null) {
        return "1h 30m"
    }
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return "${hours}h ${minutes}m"
}

fun getCompleteTMDBPath(url: String?, size: String = "w300"): String {
    return "https://image.tmdb.org/t/p/${size}${url}"
}
