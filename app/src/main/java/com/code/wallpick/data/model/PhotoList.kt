package com.code.wallpick.data.model

data class PhotoList(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)