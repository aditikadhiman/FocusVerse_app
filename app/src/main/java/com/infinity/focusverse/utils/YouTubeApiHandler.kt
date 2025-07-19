package com.infinity.focusverse.utils

import android.provider.MediaStore.Video
import com.infinity.focusverse.api.YouTubeApiService
import com.infinity.focusverse.model.VideoItem
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
//    suspend fun fetchPlaylistVideos(playlistId: String): List<YouTubeApiResponse> {
//        val allItems = mutableListOf<YouTubeApiResponse>()
//        var nextPageToken: String? = null
//
//        do {
//            val response = api.getPlaylistItems(
//                playlistId = playlistId,
//                pageToken = nextPageToken
//            )
//
//            if (!response.isSuccessful) {
//                throw Exception("YouTube Playlist API error: ${response.code()} ${response.message()}")
//            }
//
//            val items = response.body()?.items ?: emptyList()
//
//            // Collect video IDs to fetch durations
//            val videoIds = items.map { it.contentDetails.videoId }
//
//            val durationsResponse = api.getVideoDetails(
//                videoId = videoIds.joinToString(",") // You can batch request durations
//            )
//
//            val durationMap = durationsResponse.body()?.items?.associateBy({ it.id }, { it.contentDetails.duration }) ?: emptyMap()
//
//            allItems += items.map {
//                YouTubeApiResponse(
//                    videoId = it.contentDetails.videoId,
//                    title = it.snippet.title,
//                    channelName = it.snippet.channelTitle,
//                    thumbnail = it.snippet.thumbnails.medium.url,
//                    duration = durationMap[it.contentDetails.videoId] ?: "--"
//                )
//            }
//
//            nextPageToken = response.body()?.nextPageToken
//
//        } while (nextPageToken != null)
//
//        return allItems
//    }

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

data class PlaylistResponse(
    val videos: List<YouTubeVideoData>,
    val nextPageToken: String?
)

data class YouTubeVideoData(
    val videoId: String,
    val title: String,
    val channelName: String,
    val duration: String,
    val thumbnail: String
)
