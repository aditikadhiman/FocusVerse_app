package com.infinity.focusverse.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.infinity.focusverse.api.QuoteApiService
import com.infinity.focusverse.api.model.QuoteResponse
import com.infinity.focusverse.model.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val quoteApiService: QuoteApiService
) : HomeRepository {

    private val userId: String
        get() = auth.currentUser?.uid ?: throw Exception("User not authenticated")

    // 🔹 Focus Items

    override suspend fun getAllFocusItems(): List<FocusReference> {
        return firestore.collection("users")
            .document(userId)
            .collection("focusItems")
            .get()
            .await()
            .toObjects(FocusReference::class.java)
    }

    override suspend fun getFocusItem(itemId: String): FocusReference? {
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("focusItems")
            .document(itemId)
            .get()
            .await()
        return snapshot.toObject(FocusReference::class.java)
    }

    override suspend fun updateFocusItem(item: FocusReference) {
        firestore.collection("users")
            .document(userId)
            .collection("focusItems")
            .document(item.itemId)
            .set(item)
            .await()
    }

    override suspend fun removeFocusItem(itemId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("focusItems")
            .document(itemId)
            .delete()
            .await()
    }

    override suspend fun removeAllFocusItems() {
        val batch = firestore.batch()
        val items = firestore.collection("users")
            .document(userId)
            .collection("focusItems")
            .get()
            .await()
        items.documents.forEach { doc ->
            batch.delete(doc.reference)
        }
        batch.commit().await()
    }

    override suspend fun updateAllFocusItemsStatus(isCompleted: Boolean) {
        val items = getAllFocusItems()
        items.forEach { item ->
            updateFocusItem(item.copy(isCompleted = isCompleted))
        }
    }

    // 🔹 Sections

    override suspend fun getAllSections(): List<Section> {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .get()
            .await()
            .toObjects(Section::class.java)
    }

    override suspend fun addSection(section: Section) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(section.section_id)
            .set(section)
            .await()
    }

    override suspend fun updateSectionTitle(sectionId: String, newTitle: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .update("sectionName", newTitle)
            .await()
    }

    override suspend fun deleteSection(sectionId: String) {
        firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .delete()
            .await()
    }

    // 🔹 Quote

    override suspend fun getRandomQuote(): QuoteResponse {
        return quoteApiService.getRandomQuote().first()
    }

    override suspend fun getVideoItem(userId: String, sectionId: String, itemId: String): VideoItem {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("videos")
            .document(itemId)
            .get()
            .await()
            .toObject(VideoItem::class.java) ?: throw Exception("Video not found")
    }

    override suspend fun getPdfItem(userId: String, sectionId: String, itemId: String): PdfItem {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("pdfs")
            .document(itemId)
            .get()
            .await()
            .toObject(PdfItem::class.java) ?: throw Exception("Pdf not found")
    }

    override suspend fun getNoteItem(userId: String, sectionId: String, itemId: String): NoteItem {
        return firestore.collection("users")
            .document(userId)
            .collection("sections")
            .document(sectionId)
            .collection("notes")
            .document(itemId)
            .get()
            .await()
            .toObject(NoteItem::class.java) ?: throw Exception("Pdf not found")
    }

    override suspend fun getAllVideos(): List<VideoItem> {
        val videos = mutableListOf<VideoItem>()
        val sections = getAllSections()

        sections.forEach { section ->
            val sectionVideos = firestore.collection("users")
                .document(userId)
                .collection("sections")
                .document(section.section_id)
                .collection("videos")
                .get()
                .await()
                .toObjects(VideoItem::class.java)
            videos.addAll(sectionVideos)
        }

        return videos
    }

    override suspend fun getAllPdfs(): List<PdfItem> {
        val pdfs = mutableListOf<PdfItem>()
        val sections = getAllSections()

        sections.forEach { section ->
            val sectionPdfs = firestore.collection("users")
                .document(userId)
                .collection("sections")
                .document(section.section_id)
                .collection("pdfs")
                .get()
                .await()
                .toObjects(PdfItem::class.java)
            pdfs.addAll(sectionPdfs)
        }

        return pdfs
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        val notes = mutableListOf<NoteItem>()
        val sections = getAllSections()

        sections.forEach { section ->
            val sectionNotes = firestore.collection("users")
                .document(userId)
                .collection("sections")
                .document(section.section_id)
                .collection("notes")
                .get()
                .await()
                .toObjects(NoteItem::class.java)
            notes.addAll(sectionNotes)
        }

        return notes
    }


}
