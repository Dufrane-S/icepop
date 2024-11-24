package com.ssafy.icepop.data.model.dto

data class IceCreamOrder(
    val date: Long,
    val details: List<IceCreamOrderDetail>,
    val dryice: Int,
    val email: String,
    val id: Int,
    val isforhere: Int,
    val priceSum: Int,
    val spoon: Int
)