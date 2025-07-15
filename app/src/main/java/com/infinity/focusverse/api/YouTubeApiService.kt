package com.infinity.focusverse.api

//Retrofit interface for API endpoints (video + playlist)
import com.infinity.focusverse.BuildConfig
import com.infinity.focusverse.api.model.YouTubePlaylistResponse
import com.infinity.focusverse.api.model.YouTubeVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {

    // Fetch details of a single video
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("id") videoId: String,
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY
    ): Response<YouTubeVideoResponse>

    // Fetch all items in a playlist
    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 50,
        @Query("pageToken") pageToken: String? = null,
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY
    ): Response<YouTubePlaylistResponse>

}



//YouTubeApiService.kt —
// this will be your Retrofit interface to fetch video or playlist details from the YouTube Data API v3.

//💡 What each line means:
//@GET("videos") → Calls https://www.googleapis.com/youtube/v3/videos
//
//@Query("part") → Tells YouTube which data you want (title, duration, etc.)
//
//@Query("id") → The YouTube video ID (like "dQw4w9WgXcQ")
//
//@Query("key") → Your YouTube API key
//
//Response<YouTubeVideoResponse> → Wraps the JSON response in a model you made