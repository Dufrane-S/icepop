package com.ssafy.icepop.data.model.dto

data class Member(
    val age: Int,
    val discountRate: Double,
    val email: String,
    val gender: Int,
    val level: String,
    val name: String,
    val nextLvRemain: Int
) {

    constructor() : this(
        age = 0,
        email = "",
        discountRate = 0.0,
        gender = 0,
        level = "",
        name = "",
        nextLvRemain = 0
    )

    // 보조 생성자
    constructor(age: Int, email: String, gender: Int) : this(
        age = age,
        email = email,
        discountRate = 0.0,
        gender = gender,
        level = "",
        name = "",
        nextLvRemain = 0
    )
}
