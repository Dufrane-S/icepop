package com.ssafy.icepop.data.model.dto

data class User(
    val email: String,
    val password: String,
    val name: String = "",
    val age: Int = 0,
    val gender: Int = 0,
) {
    constructor(email: String, password: String) : this(email, password, "", 0, 0)
}