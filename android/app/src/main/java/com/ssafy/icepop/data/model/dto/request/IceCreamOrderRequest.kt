package com.ssafy.icepop.data.model.dto.request

import com.ssafy.icepop.data.model.dto.IceCreamCartItem

data class IceCreamOrderRequest(
    val details: List<IceCreamCartItem>,
    val dryice: Int,
    val email: String,
    val isForHere: Int,
    val spoon: Int,
    val priceSum: Int,
    val discountSum: Int,
    val resultSum: Int
)