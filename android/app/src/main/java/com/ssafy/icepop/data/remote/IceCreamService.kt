package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.IceCream
import com.ssafy.icepop.data.model.dto.request.IceCreamRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IceCreamService {
    @POST("ice/list-with-con")
    suspend fun getAllIceCream(@Body iceCreamRequest: IceCreamRequest) : List<IceCream>

    @GET("ai/recommand/{email}")
    suspend fun getIceCreamByAI(@Path("email") email: String) : List<IceCream>

    @GET("ice/{id}")
    suspend fun getIceCreamById(@Path("id") id: Int) : IceCream
}