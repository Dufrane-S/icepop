package com.ssafy.icepop.data.model.dto.request

data class ReviewListRequest(
    val email: String,
    val orderId: Int,
    val recent: Boolean
)