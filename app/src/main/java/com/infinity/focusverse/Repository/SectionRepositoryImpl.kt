package com.infinity.focusverse.Repository

//
//class SectionRepositoryImpl(
//    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
//) : SectionRepository {
//
//    override suspend fun getSection(sectionId: String, userId: String): Section {
//        val doc = firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .get().await()
//
//        return doc.toObject(Section::class.java)?.copy(section_id = doc.id)
//            ?: throw Exception("Section not found")
//    }
//
//    override suspend fun getVideos(sectionId: String, userId: String): List<VideoItem> {
//        val snapshot = firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .collection("videos")
//            .get().await()
//
//        return snapshot.documents.mapNotNull {
//            it.toObject(VideoItem::class.java)?.copy(id = it.id)
//        }
//    }
//
//    override suspend fun getPdfs(sectionId: String, userId: String): List<PdfItem> {
//        val snapshot = firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .collection("pdfs")
//            .get().await()
//
//        return snapshot.documents.mapNotNull {
//            it.toObject(PdfItem::class.java)?.copy(id = it.id)
//        }
//    }
//
//    override suspend fun getNotes(sectionId: String, userId: String): List<NoteItem> {
//        val snapshot = firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .collection("notes")
//            .get().await()
//
//        return snapshot.documents.mapNotNull {
//            it.toObject(NoteItem::class.java)?.copy(id = it.id)
//        }
//    }
//
//    override suspend fun getSubsections(sectionId: String, userId: String): List<Subsection> {
//        val subsectionSnapshot = firestore.collection("users")
//            .document(userId)
//            .collection("sections")
//            .document(sectionId)
//            .collection("subsections")
//            .get().await()
//
//        return subsectionSnapshot.documents.mapNotNull { doc ->
//            val subSectionId = doc.id
//            val baseSubsection = doc.toObject(Subsection::class.java)?.copy(subSection_id = subSectionId)
//
//            baseSubsection?.let { subsection ->
//                val videos = firestore.collection("users")
//                    .document(userId)
//                    .collection("sections")
//                    .document(sectionId)
//                    .collection("subsections")
//                    .document(subSectionId)
//                    .collection("videos")
//                    .get().await()
//                    .documents.mapNotNull { it.toObject(VideoItem::class.java)?.copy(id = it.id) }
//
//                val notes = firestore.collection("users")
//                    .document(userId)
//                    .collection("sections")
//                    .document(sectionId)
//                    .collection("subsections")
//                    .document(subSectionId)
//                    .collection("notes")
//                    .get().await()
//                    .documents.mapNotNull { it.toObject(NoteItem::class.java)?.copy(id = it.id) }
//
//                val pdfs = firestore.collection("users")
//                    .document(userId)
//                    .collection("sections")
//                    .document(sectionId)
//                    .collection("subsections")
//                    .document(subSectionId)
//                    .collection("pdfs")
//                    .get().await()
//                    .documents.mapNotNull { it.toObject(PdfItem::class.java)?.copy(id = it.id) }
//
//                subsection.copy(videos = videos, notes = notes, pdfs = pdfs)
//            }
//        }
//    }
//}
