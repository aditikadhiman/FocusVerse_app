package com.infinity.focusverse.utils

import com.infinity.focusverse.api.YouTubeApiService
import com.infinity.focusverse.model.YouTubeApiResponse
import javax.inject.Inject

class YouTubeApiHandler @Inject constructor(
    private val api: YouTubeApiService
) {

    suspend fun fetchSingleVideo(videoId: String): YouTubeApiResponse {
        val response = api.getVideoDetails(videoId = videoId)

        if (!response.isSuccessful) {
            throw Exception("YouTube API error: ${response.code()} ${response.message()}")
        }

        val item = response.body()?.items?.firstOrNull()
            ?: throw Exception("Video not found")

        return YouTubeApiResponse(
            videoId = item.id,
            title = item.snippet.title,
            channelName = item.snippet.channelTitle,
            thumbnail = item.snippet.thumbnails.medium.url,
            duration = parseDuration(item.contentDetails.duration)
        )
    }

    suspend fun fetchPlaylistVideos(playlistId: String): List<YouTubeApiResponse> {
        val response = api.getPlaylistItems(playlistId = playlistId)

        if (!response.isSuccessful) {
            throw Exception("YouTube Playlist API error: ${response.code()} ${response.message()}")
        }

        return response.body()?.items?.map {
            YouTubeApiResponse(
                videoId = it.contentDetails.videoId,
                title = it.snippet.title,
                channelName = it.snippet.channelTitle,
                thumbnail = it.snippet.thumbnails.medium.url,
                duration = "--"
            )
        } ?: emptyList()
    }

    private fun parseDuration(isoDuration: String): String {
        val regex = Regex("PT(?:(\\d+)M)?(?:(\\d+)S)?")
        val matchResult = regex.find(isoDuration)
        val minutes = matchResult?.groups?.get(1)?.value ?: "0"
        val seconds = matchResult?.groups?.get(2)?.value ?: "00"
        return "$minutes:$seconds"
    }
}