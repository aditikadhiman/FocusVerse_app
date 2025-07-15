package com.infinity.focusverse.model

data class VideoItem(
    val id: String = "",         // YouTube video ID (e.g. "abc123")
    val title: String = "",           // Video title
    val channel: String = "",         // Channel name
    val duration: String = "",        // Duration like "10:05"
    val thumbnailUrl: String = "",    // Full URL of thumbnail
    val isCompleted: Boolean = false, // Whether user marked it done
    val sectionId: String = "",        // (Optional) To trace where it came from
    val videoUrl: String="",
    val subSectionId: String = ""
)
