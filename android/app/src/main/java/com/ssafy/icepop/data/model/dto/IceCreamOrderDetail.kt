package com.ssafy.icepop.data.model.dto

data class IceCreamOrderDetail(
    val discountedPrice: Int,
    val iceName: String,
    val id: Int,
    val img: String,
    val isEvent: Int,
    val orderId: Int,
    val price: Int,
    val productId: Int,
    val quantity: Int
)