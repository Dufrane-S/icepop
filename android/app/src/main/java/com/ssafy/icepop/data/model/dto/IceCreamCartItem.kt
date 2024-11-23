package com.ssafy.icepop.data.model.dto

data class IceCreamCartItem(
    val productId: Int,
    val name: String,
    val price: Int,
    var quantity: Int,
    val img: String
)
