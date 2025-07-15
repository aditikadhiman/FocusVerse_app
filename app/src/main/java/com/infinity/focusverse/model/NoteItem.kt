package com.infinity.focusverse.model

data class NoteItem(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val isCompleted: Boolean = false,
    val sectionId: String = "",
    val subSectionId: String = ""
)
