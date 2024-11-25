package com.ssafy.icepop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.ssafy.icepop.data.model.dto.IceCreamCartItem
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil
import kotlin.math.floor

class ActivityViewModel : ViewModel() {
    private val _cartItems: MutableLiveData<MutableMap<Int, IceCreamCartItem>> = MutableLiveData(mutableMapOf())
    val cartItems: LiveData<MutableMap<Int, IceCreamCartItem>> get() = _cartItems

    // 장바구니가 비어있는 상태를 나타내는 LiveData
    private val _isCartEmpty: MutableLiveData<Boolean> = MutableLiveData(true)
    val isCartEmpty: LiveData<Boolean> get() = _isCartEmpty

    private val _discountRate: MutableLiveData<Double> = MutableLiveData(0.0) // 할인율 퍼센트
    val discountRate: LiveData<Double> get() = _discountRate

    // 전체 금액
    val totalPrice: LiveData<Int> = _cartItems.map { items ->
        items.values.sumOf { it.price * it.quantity }
    }

    // 할인 금액
    val discountAmount: MutableLiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(totalPrice) { calculateDiscount(it, _discountRate.value ?: 0.0) }
        addSource(_discountRate) { calculateDiscount(totalPrice.value ?: 0, it) }
    }

    // 최종 금액
    val finalPrice: MutableLiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(totalPrice) { updateFinalPrice(it, discountAmount.value ?: 0) }
        addSource(discountAmount) { updateFinalPrice(totalPrice.value ?: 0, it) }
    }

    private val _orderItemClick : MutableLiveData<Boolean> = MutableLiveData(false)
    val orderItemClick: LiveData<Boolean> get() = _orderItemClick

    fun addToCart(item: IceCreamCartItem) {
        val currentCart = _cartItems.value ?: mutableMapOf()

        if (currentCart.containsKey(item.productId)) {
            currentCart[item.productId]?.let {
                it.quantity += item.quantity
            }
        } else {
            currentCart[item.productId] = item
        }

        _cartItems.value = currentCart
        _isCartEmpty.value = currentCart.isEmpty()
    }

    fun removeFromCart(productId: Int, decrementQuantity: Int = 1) {
        val currentCart = _cartItems.value ?: return

        if (currentCart.containsKey(productId)) {
            val item = currentCart[productId]
            item?.let {
                it.quantity -= decrementQuantity
                if (it.quantity <= 0) {
                    currentCart.remove(productId)
                } else {
                    currentCart[productId] = it.copy(quantity = it.quantity)
                }
            }
        }

        _cartItems.value = currentCart.toMap().toMutableMap()
        _isCartEmpty.value = currentCart.isEmpty()
    }


    fun resetCart() {
        _cartItems.value = mutableMapOf()
        _isCartEmpty.value = true
    }

    private fun calculateDiscount(total: Int, rate: Double) {
        val discount = ceil(total * rate / 10) * 10
        discountAmount.value = total - discount.toInt() // 정수로 변환
    }

    private fun updateFinalPrice(total: Int, discount: Int) {
        // 할인 후 금액을 버림 처리
        finalPrice.value = floor((total - discount) / 10.0).toInt() * 10
    }

    fun setDiscountRate(rate: Double) {
        _discountRate.value = rate
    }

    fun setOrderItemClick() {
        _orderItemClick.value  = true
    }

    fun resetOrderItemClick() {
        _orderItemClick.value  = false
    }
}