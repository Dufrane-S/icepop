package com.ssafy.icepop.data.model.dto

data class IceCreamOrderReview(
    val content: String,
    val date: Long,
    val memberEmail: String,
    val name: String,
    val orderId: Int,
    val rate: Double
)