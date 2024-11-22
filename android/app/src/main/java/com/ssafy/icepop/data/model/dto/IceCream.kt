package com.ssafy.icepop.data.model.dto

data class IceCream(
    val id: Int,
    val name: String,
    val content: String,

    val price: Int,
    val count: Int,
    val isEvent: Int,
    val kcal: Int,

    val type: String,
    val img: String
)