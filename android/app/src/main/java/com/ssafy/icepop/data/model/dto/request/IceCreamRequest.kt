package com.ssafy.icepop.data.model.dto.request

data class IceCreamRequest(
    val age: Int,
    val gender: Int,
    val rate: Int,
    val type: String?
) {
    // 기본 생성자(빈 객체를 생성하고 싶을 때)
    constructor() : this(0, 0, 0, null)
}