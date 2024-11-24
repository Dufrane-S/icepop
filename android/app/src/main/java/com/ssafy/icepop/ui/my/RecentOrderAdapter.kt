package com.ssafy.icepop.ui.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.databinding.ItemRecentOrderBinding
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.util.CommonUtils

class RecentOrderAdapter(
    var iceCreamOrderList : List<IceCreamOrder>,
    private val onItemClickListener: (IceCreamOrder) -> Unit
) : RecyclerView.Adapter<RecentOrderAdapter.RecentOrderViewHolder>() {

    inner class RecentOrderViewHolder(private val binding : ItemRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(iceCreamOrder: IceCreamOrder) {
            val primaryName = if (iceCreamOrder.details.isNotEmpty()) {
                iceCreamOrder.details[0].iceName
            }
            else {
                "아이템 없음"
            }

            val primaryImage = if (iceCreamOrder.details.isNotEmpty()) {
                iceCreamOrder.details[0].img
            }
            else {
                "이미지 없음"
            }

            val additionalCount = iceCreamOrder.details.size - 1

            val displayText = if (additionalCount > 0) {
                "$primaryName 외 ${additionalCount}개"
            } else {
                primaryName
            }

            binding.recentOrderName.text = displayText
            binding.recentOrderPrice.text = CommonUtils.makeComma(iceCreamOrder.resultSum)
            binding.recentOrderDateTimeTv.text = CommonUtils.formatLongToDateTime(iceCreamOrder.date)

            val imageUrl = ICE_CREAM_IMAGE_BASE_URL + primaryImage

            Glide.with(binding.root).load(imageUrl).into(binding.recentOrderImage)

            binding.root.setOnClickListener { onItemClickListener(iceCreamOrder) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentOrderViewHolder {
        val binding = ItemRecentOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentOrderViewHolder(binding)
    }

    override fun getItemCount(): Int = iceCreamOrderList.size

    override fun onBindViewHolder(holder: RecentOrderViewHolder, position: Int) {
        holder.bind(iceCreamOrderList[position])
    }
}