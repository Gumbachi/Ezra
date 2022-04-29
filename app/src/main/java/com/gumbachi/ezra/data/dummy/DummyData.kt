package com.gumbachi.ezra.data.dummy

import com.gumbachi.ezra.model.*

object DummyData {
    val movies = listOf(
        Movie(1, "Deadpool", "9-7-2000", "1h23m", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fSRb7vyIP8rQpL0I47P3qUsEKX3.jpg", 10.0, WatchStatus.WATCHING),
        Movie(2, "Deadpool 2", "9-7-2000", "1h23m", "", 8.0, WatchStatus.WATCHING),
        Movie(3, "Deadpool 3","9-7-2000", "1h23m", "", 9.0, WatchStatus.WATCHING),
        Movie(4, "Deadpool 4","9-7-2000", "1h23m", "", 6.0, WatchStatus.WATCHING),
        Movie(5, "Deadpool 5","9-7-2000", "1h23m","", 3.0, WatchStatus.WATCHING),
        Movie(6, "Deadpool 6","9-7-2000", "1h23m", "", 9.0, WatchStatus.WATCHING),
        Movie(7, "Deadpool 7","9-7-2000", "1h23m", "", 6.0, WatchStatus.WATCHING),
        Movie(8, "Deadpool 8","9-7-2000", "1h23m","", 3.0, WatchStatus.WATCHING),
    )

    val movies2 = listOf(
        Movie(9, "Deadpool 9", "9-7-2000", "1h23m", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fb9zF01GKOkNziYVusg20laWsGh.jpg", 10.0, WatchStatus.PLANNING),
        Movie(10, "Deadpool 10", "9-7-2000", "1h23m", "", 8.0, WatchStatus.PLANNING),
        Movie(11, "Deadpool 11", "9-7-2000", "1h23m", "", 9.0, WatchStatus.PLANNING),
        Movie(12, "Deadpool 12", "9-7-2000", "1h23m", "", 6.0, WatchStatus.PLANNING),
    )

    val movies3 = listOf(
        Movie(13, "Deadpool 13", "9-7-2000", "1h23m", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mINJaa34MtknCYl5AjtNJzWj8cD.jpg", 10.0, WatchStatus.COMPLETED),
        Movie(14, "Deadpool 14", "9-7-2000", "1h23m", "", 8.0, WatchStatus.COMPLETED),
        Movie(15, "Deadpool 15", "9-7-2000", "1h23m", "", 9.0, WatchStatus.COMPLETED),
    )

    val movie = Movie(
        16,
        "Deadpool 16",
        "9-7-2000",
        "1h23m",
        "",
        8.0,
        WatchStatus.COMPLETED
    )

    val shows = listOf(
        Show(17, "ERASED", "9-7-2000", "", 8.0, WatchStatus.WATCHING,4, 12),
        Show(18, "Mob Psycho", "9-7-2000", "", 9.0, WatchStatus.WATCHING,24, 24),
        Show(19, "Fairy Tail", "9-7-2000", "", 4.0, WatchStatus.WATCHING,106, 128)
    )

    val show = Show(
        20,
        "Attack On Titan",
        "9-7-2000",
        "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx131681-ODIRpBIbR5Eu.jpg",
        10.0,
        WatchStatus.WATCHING,
        106,
        128
    )

    val animelist = listOf(
        Anime(21, "ERASED","9-7-2000",  8.0, WatchStatus.WATCHING,4, 12, ""),
        Anime(22, "Mob Psycho","9-7-2000", 9.0, WatchStatus.WATCHING,24, 24, ""),
        Anime(23, "Fairy Tail","9-7-2000",  4.0, WatchStatus.WATCHING,106, 128, "")
    )

    val anime = Anime(
        20,
        "Attack On Titan",
        "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx131681-ODIRpBIbR5Eu.jpg",
        10.0,
        WatchStatus.WATCHING,
        106,
        128,
        "9-7-2000",
    )
}