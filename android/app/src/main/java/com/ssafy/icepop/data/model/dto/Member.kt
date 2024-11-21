package com.ssafy.icepop.data.model.dto

data class Member(
    val age: Int,
    val discountRate: Double,
    val email: String,
    val gender: Int,
    val level: String,
    val name: String,
    val nextLvRemain: Int
)