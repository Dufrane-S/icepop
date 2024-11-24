package com.ssafy.icepop.data.model.dto

data class IceCreamOrderDetail(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int
)