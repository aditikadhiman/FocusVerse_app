package com.infinity.focusverse.model

//data class Subsection(
//    val subSection_id: String = "",
//    val title: String = "",
//    val videos: List<VideoItem> = emptyList(),
//    val notes: List<NoteItem> = emptyList(),
//    val pdfs: List<PdfItem> = emptyList(),
//    val sectionId: String=""
//)

data class Subsection(
    val subSection_id: String = "",
    val title: String = "",
    val sectionId: String = ""    // for reverse lookup (not required, but helpful)
)
