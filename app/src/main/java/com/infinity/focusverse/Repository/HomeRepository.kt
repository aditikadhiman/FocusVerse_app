package com.infinity.focusverse.Repository

import com.infinity.focusverse.api.model.QuoteResponse
import com.infinity.focusverse.model.*

interface HomeRepository {

    // 🔹 Focus Items
    suspend fun getAllFocusItems(): List<FocusReference>
    suspend fun getFocusItem(itemId: String): FocusReference?
    suspend fun updateFocusItem(item: FocusReference)
    suspend fun removeFocusItem(itemId: String)
    suspend fun removeAllFocusItems()
    suspend fun updateAllFocusItemsStatus(isCompleted: Boolean)

    // 🔹 Sections
    suspend fun getAllSections(): List<Section>
    suspend fun addSection(section: Section)
    suspend fun updateSectionTitle(sectionId: String, newTitle: String)
    suspend fun deleteSection(sectionId: String)

    // 🔹 Quote
    suspend fun getRandomQuote(): QuoteResponse

    // 🔹 Individual fetch by type
    suspend fun getVideoItem(userId: String, sectionId: String, itemId: String): VideoItem
    suspend fun getPdfItem(userId: String, sectionId: String, itemId: String): PdfItem
    suspend fun getNoteItem(userId: String, sectionId: String, itemId: String): NoteItem

    // 🔹 Fetch all (used in HomeViewModel's fetchFocusItems)
    suspend fun getAllVideos(): List<VideoItem>
    suspend fun getAllPdfs(): List<PdfItem>
    suspend fun getAllNotes(): List<NoteItem>
}
