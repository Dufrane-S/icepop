package com.ssafy.icepop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.icepop.data.model.dto.IceCreamCartItem

class ActivityViewModel : ViewModel() {
    private val _cartItems: MutableLiveData<MutableMap<Int, IceCreamCartItem>> = MutableLiveData(mutableMapOf())
    val cartItems: LiveData<MutableMap<Int, IceCreamCartItem>> get() = _cartItems

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
    }
}