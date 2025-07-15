package com.infinity.focusverse.model

data class YouTubeApiResponse(
    val videoId: String,
    val title: String,
    val channelName: String,
    val thumbnail: String,
    val duration: String
)