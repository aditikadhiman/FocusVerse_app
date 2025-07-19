package com.infinity.focusverse.api.model

//Data models for parsing the YouTube API JSON responses.

// For single video fetch
data class YouTubeVideoResponse(
    val items: List<VideoItem>
)

data class VideoItem(
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails
)

data class Snippet(
    val title: String,
    val channelTitle: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(
    val medium: Thumbnail
)

data class Thumbnail(
    val url: String
)

data class ContentDetails(
    val duration: String // e.g. "PT5M30S"
)

// For playlist fetch
data class YouTubePlaylistResponse(
    val nextPageToken: String?, // Nullable
    val items: List<PlaylistItem>
)

data class PlaylistItem(
    val snippet: PlaylistSnippet,
    val contentDetails: PlaylistContentDetails
)

data class PlaylistSnippet(
    val title: String,
    val channelTitle: String,
    val thumbnails: Thumbnails
)

data class PlaylistContentDetails(
    val videoId: String
)


//{
//  "items": [
//    {
//      "id": "abc123",
//      "snippet": {
//        "title": "Recursion for Beginners",
//        "channelTitle": "FreeCodeCamp",
//        "thumbnails": {
//          "medium": {
//            "url": "https://i.ytimg.com/..."
//          }
//        }
//      },
//      "contentDetails": {
//        "duration": "PT12M35S"
//      }
//    }
//  ]
//}

