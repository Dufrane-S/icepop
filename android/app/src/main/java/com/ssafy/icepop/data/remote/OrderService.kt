package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.request.IceCreamOrderRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {
    @POST("order/makeOrder")
    suspend fun makeOrder(@Body request: IceCreamOrderRequest) : String
}