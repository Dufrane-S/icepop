package com.ssafy.icepop.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.icepop.data.model.dto.IceCreamOrderDetail
import com.ssafy.icepop.databinding.ItemOrderDetailBinding
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.util.CommonUtils

class OrderDetailListAdapter (
    var iceCreamOrderDetails : List<IceCreamOrderDetail>,
) : RecyclerView.Adapter<OrderDetailListAdapter.OrderDetailViewHolder>(){
    inner class OrderDetailViewHolder(private val binding : ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(iceCreamOrderDetail : IceCreamOrderDetail) {
            val imageUrl = ICE_CREAM_IMAGE_BASE_URL + iceCreamOrderDetail.img
            Glide.with(binding.root).load(imageUrl).into(binding.iceCreamImage)

            binding.iceCreamName.text = iceCreamOrderDetail.iceName
            binding.iceCreamQuantity.text = "수량: ${iceCreamOrderDetail.quantity}"
            binding.iceCreamPrice.text = CommonUtils.makeComma(iceCreamOrderDetail.discountedPrice * iceCreamOrderDetail.quantity)
        }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = iceCreamOrderDetails.size

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(iceCreamOrderDetails[position])
    }
}
