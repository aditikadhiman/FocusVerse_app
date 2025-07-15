package com.infinity.focusverse.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.infinity.focusverse.Repository.*
import com.infinity.focusverse.api.QuoteApiService
import com.infinity.focusverse.api.YouTubeApiService
import com.infinity.focusverse.utils.YouTubeApiHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

//    @Provides
//    @Singleton
//    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()


    @Provides
    @Singleton
    fun provideHomeRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth,
        quoteApiService: QuoteApiService
    ): HomeRepository = HomeRepositoryImpl(firestore,auth,quoteApiService)

//    @Provides
//    @Singleton
//    fun provideAddItemRepository(
//        firestore: FirebaseFirestore,
//        auth: FirebaseAuth
//    ): AddItemRepositoryImpl = AddItemRepositoryImpl(firestore, auth)

    @Provides
    @Singleton
    fun provideYouTubeApiService(): YouTubeApiService =
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YouTubeApiService::class.java)

    @Provides
    @Singleton
    fun provideSectionRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): SectionRepository = SectionRepositoryImpl(firestore,auth)

    @Provides
    @Singleton
    fun provideQuoteApiService(): QuoteApiService =
        Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApiService::class.java)

    @Provides
    @Singleton
    fun provideYouTubeApiHandler(api: YouTubeApiService): YouTubeApiHandler =
        YouTubeApiHandler(api)


    @Provides
    @Singleton
    fun provideAddItemRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth,
        youTubeApiHandler: YouTubeApiHandler
    ): AddItemRepository = AddItemRepositoryImpl(firestore, auth, youTubeApiHandler)

}
