package com.infinity.focusverse.utils

class DateUtils {
}

//data class NoteItem(
//    val id: String = "",
//    val title: String = "",
//    val content: String = "",
//    val isCompleted: Boolean = false,
//    val sectionId: String = "",
//    val subSectionId: String = ""
//)
//
//data class PdfItem(
//    val id: String = "",
//    val title: String = "",
//    val pdfUrl: String = "",
//    val isCompleted: Boolean = false,
//    val sectionId: String = "",
//    val subSectionId: String = ""
//)
//
//data class Section(
//    val section_id: String = "",
//    val sectionName: String = "",
//    val videos: List<VideoItem> = emptyList(),        // Items directly under section
//    val pdfs: List<PdfItem> = emptyList(),
//    val notes: List<NoteItem> = emptyList(),
//    val subsections: List<Subsection> = emptyList()   // Nested subsections (optional)
//    , val userId: String = ""
//)
//
//data class Subsection(
//    val subSection_id: String = "",
//    val title: String = "",
//    val videos: List<VideoItem> = emptyList(),
//    val notes: List<NoteItem> = emptyList(),
//    val pdfs: List<PdfItem> = emptyList(),
//    val sectionId: String=""
//)
//
//data class VideoItem(
//    val id: String = "",         // YouTube video ID (e.g. "abc123")
//    val title: String = "",           // Video title
//    val channel: String = "",         // Channel name
//    val duration: String = "",        // Duration like "10:05"
//    val thumbnailUrl: String = "",    // Full URL of thumbnail
//    val isCompleted: Boolean = false, // Whether user marked it done
//    val sectionId: String = "",        // (Optional) To trace where it came from
//    val videoUrl: String="",
//    val subSectionId: String = ""
//)
//
//data class YouTubeApiResponse(
//    val videoId: String,
//    val title: String,
//    val channelName: String,
//    val thumbnail: String,
//    val duration: String
//)
//
//data class YouTubeVideoResponse(
//    val items: List<VideoItem>
//)
//
//data class VideoItem(
//    val id: String,
//    val snippet: Snippet,
//    val contentDetails: ContentDetails
//)
//
//data class Snippet(
//    val title: String,
//    val channelTitle: String,
//    val thumbnails: Thumbnails
//)
//
//data class Thumbnails(
//    val medium: Thumbnail
//)
//
//data class Thumbnail(
//    val url: String
//)
//
//data class ContentDetails(
//    val duration: String  // ISO 8601 duration format (e.g. PT5M30S)
//)
//
//
//// For playlist fetch
//data class YouTubePlaylistResponse(
//    val items: List<PlaylistItem>
//)
//
//data class PlaylistItem(
//    val snippet: PlaylistSnippet,
//    val contentDetails: PlaylistContentDetails
//)
//
//data class PlaylistSnippet(
//    val title: String,
//    val channelTitle: String,
//    val thumbnails: Thumbnails
//)
//
//data class PlaylistContentDetails(
//    val videoId: String
//)
//
