package com.ssafy.icepop.data.remote

import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.icepop.data.model.dto.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    // 로그인 처리 후 성공적으로 로그인 되었다면 loginId라는 쿠키를 내려준다.

    @GET("test")
    suspend fun test() : String


    @POST("member/login")
    suspend fun login(@Body body: User): String
}

object Api {
    val userService : UserService by lazy {
        ApplicationClass.retrofit.create(UserService::class.java)
    }
}
