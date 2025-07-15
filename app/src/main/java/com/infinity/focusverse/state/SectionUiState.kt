package com.infinity.focusverse.state

import com.infinity.focusverse.model.*


data class SectionUiState(
    val section: Section? = null,
    val subSections: List<Subsection> = emptyList(),
    val videos: List<VideoItem> = emptyList(),
    val pdfs: List<PdfItem> = emptyList(),
    val notes: List<NoteItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
