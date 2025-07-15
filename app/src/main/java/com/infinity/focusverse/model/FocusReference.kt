package com.infinity.focusverse.model

data class FocusReference(
    val itemId: String = "",           // ID of the original item (video/pdf/note)
    val sectionId: String = "",        // ID of the section where the item belongs
    val type: String = "",             // "video", "pdf", or "note"
    val title: String = "",            // Title to display in Today's Focus
    val isCompleted: Boolean = false
)