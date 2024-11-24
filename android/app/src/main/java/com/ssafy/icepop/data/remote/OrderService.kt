package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.data.model.dto.request.IceCreamOrderRequest
import com.ssafy.icepop.data.model.dto.request.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {
    @POST("order/makeOrder")
    suspend fun makeOrder(@Body request: IceCreamOrderRequest) : Response<String>

    @POST("order/orderList")
    suspend fun getOrderList(@Body request: OrderRequest) : List<IceCreamOrder>
}