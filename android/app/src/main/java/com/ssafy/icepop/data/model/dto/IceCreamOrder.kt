package com.ssafy.icepop.data.model.dto

data class IceCreamOrder(
    val date: Long,
    val details: List<IceCreamOrderDetail>,
    val dryice: Int,
    val email: String,
    val id: Int,
    val isforhere: Int,
    val priceSum: Int,
    val discountSum: Int,
    val resultSum: Int,
    val spoon: Int
)