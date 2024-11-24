package com.ssafy.icepop.data.model.dto.request

data class OrderRequest(
    val email: String,
    val orderId: Int,
    val recent: Boolean
) {
    constructor() : this("", 0, false)
    constructor(recent: Boolean) : this("", 0, recent)
}