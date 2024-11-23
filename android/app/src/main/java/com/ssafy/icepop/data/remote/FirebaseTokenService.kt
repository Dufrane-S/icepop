package com.ssafy.icepop.data.remote

import retrofit2.http.POST
import retrofit2.http.Query

interface FirebaseTokenService {
    // Token정보 서버로 전송
    @POST("token")
    suspend fun uploadToken(@Query("token") token: String): String

    @POST("broadcast")
    fun broadcast(@Query("title") title: String, @Query("body") body: String ): String
}