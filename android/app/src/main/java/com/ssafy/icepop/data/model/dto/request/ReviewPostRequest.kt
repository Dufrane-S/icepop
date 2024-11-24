package com.ssafy.icepop.data.model.dto.request

data class ReviewPostRequest(
    val content: String,
    val email: String,
    val orderId: Int,
    val rate: Double
)