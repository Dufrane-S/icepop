package com.ssafy.icepop.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.IceCreamCartItem
import com.ssafy.icepop.databinding.ItemCartBinding
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL

class IceCreamCartAdapter(private val cartItems: List<IceCreamCartItem>) : RecyclerView.Adapter<IceCreamCartAdapter.IceCreamCartViewHolder>() {

    class IceCreamCartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: IceCreamCartItem) {
            val imageUrl = ICE_CREAM_IMAGE_BASE_URL + cartItem.img
            Glide.with(binding.root).load(imageUrl).into(binding.iceCreamImage)

            binding.iceCreamName.text = cartItem.name
            binding.iceCreamPrice.text = "${cartItem.price} 원"
            binding.iceCreamQuantity.text = "수량: ${cartItem.quantity}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IceCreamCartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IceCreamCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IceCreamCartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = cartItems.size
}