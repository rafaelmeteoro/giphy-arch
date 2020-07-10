package com.meteoro.repository.data

data class Gif(
    val id: String,
    val url: String,
    val title: String,
    val favorite: Boolean = false
)
