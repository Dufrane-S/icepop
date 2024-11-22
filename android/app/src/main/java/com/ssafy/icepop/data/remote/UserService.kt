package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.icepop.data.model.dto.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("member/login")
    suspend fun login(@Body user: User): Member

    @POST("member/join")
    suspend fun signup(@Body user: User)

    @GET("member/isUsed/{email}")
    suspend fun isUsedEmail(@Path("email") email: String) : Boolean
}
