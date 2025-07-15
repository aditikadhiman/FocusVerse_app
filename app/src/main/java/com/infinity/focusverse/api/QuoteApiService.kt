package com.infinity.focusverse.api

import com.infinity.focusverse.api.model.QuoteResponse
import retrofit2.http.GET

//data class QuoteResponse(
//    val content: String,
//    val author: String
//)

interface QuoteApiService {
    @GET("random")
    suspend fun getRandomQuote(): List<QuoteResponse>
}
