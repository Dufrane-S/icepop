package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.icepop.data.model.dto.User
import com.ssafy.icepop.data.model.dto.request.TokenRequest
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

    @GET("member/info/{email}")
    suspend fun getMyInfo(@Path("email") email: String): Member

    @POST("member/setToken")
    suspend fun registerFcmToken(@Body tokenRequest: TokenRequest): String
}
