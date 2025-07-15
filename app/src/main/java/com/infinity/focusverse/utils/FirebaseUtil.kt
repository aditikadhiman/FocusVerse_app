package com.infinity.focusverse.utils

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtils {

    private val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    private val firestore: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun getUserId(): String? = auth.currentUser?.uid

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun getUsersCollection(): CollectionReference =
        firestore.collection("users")

    fun getUserDocument(): DocumentReference =
        getUsersCollection().document(getUserId() ?: "unknown")

    fun getSectionsCollection(): CollectionReference =
        getUserDocument().collection("sections")

    fun getCurrentTimestamp(): Timestamp = Timestamp.now()

    fun getSubsectionsCollection(sectionId: String): CollectionReference =
        getSectionsCollection().document(sectionId).collection("subsections")
}
