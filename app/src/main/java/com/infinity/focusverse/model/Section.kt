package com.infinity.focusverse.model

//data class Section(
//    val section_id: String = "",
//    val sectionName: String = "",
//    val videos: List<VideoItem> = emptyList(),        // Items directly under section
//    val pdfs: List<PdfItem> = emptyList(),
//    val notes: List<NoteItem> = emptyList(),
//    val subsections: List<Subsection> = emptyList()   // Nested subsections (optional)
//, val userId: String = ""
//)

data class Section(
    val section_id: String = "",
    val sectionName: String = "",
    val userId: String = ""
)