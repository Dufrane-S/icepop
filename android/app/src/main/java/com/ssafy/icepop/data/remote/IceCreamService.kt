package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.IceCream
import com.ssafy.icepop.data.model.dto.request.IceCreamRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface IceCreamService {
    @POST("ice/list-with-con")
    suspend fun getAllIceCream(@Body iceCreamRequest: IceCreamRequest) : List<IceCream>
}