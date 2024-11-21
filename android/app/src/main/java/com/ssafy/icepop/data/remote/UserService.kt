package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.Member
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.icepop.data.model.dto.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("member/login")
    suspend fun login(@Body user: User): Member

    @POST("member/join")
    suspend fun signup(@Body user : User)
}
