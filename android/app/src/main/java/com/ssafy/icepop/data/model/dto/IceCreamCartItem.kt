package com.ssafy.icepop.data.model.dto

import com.google.gson.annotations.SerializedName

data class IceCreamCartItem(
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    var quantity: Int,
    val name: String,
    val price: Int,
    val img: String
)
