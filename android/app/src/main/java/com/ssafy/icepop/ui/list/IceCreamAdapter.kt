package com.ssafy.icepop.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.icepop.databinding.ItemIcecreamBinding
import com.ssafy.icepop.data.model.dto.IceCream
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL
import com.ssafy.smartstore_jetpack.util.CommonUtils

class IceCreamAdapter(
    var iceCreamList: List<IceCream>,
    private val onItemClickListener: (IceCream) -> Unit
) : RecyclerView.Adapter<IceCreamAdapter.IceCreamViewHolder>() {

    inner class IceCreamViewHolder(private val binding: ItemIcecreamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(iceCream: IceCream) {
            binding.iceCreamName.text = iceCream.name

            //아이스크림이 이벤트를 한다.
            if (iceCream.isEvent != 0) {
                binding.discountArea.visibility = View.VISIBLE
                // 원래 가격 표시
                binding.originalPriceTv.text = CommonUtils.makeComma(iceCream.price)

                binding.discountRateTv.text = "-${iceCream.isEvent}%"

                val rate = iceCream.isEvent * 0.01

                val discountedPrice = Math.ceil(iceCream.price * (1 - rate)).toInt()

                val totalPrice = Math.floor((discountedPrice / 10.0)).toInt() * 10

                binding.iceCreamTotalPrice.text = CommonUtils.makeComma(totalPrice)
            }
            //아이스크림 이벤트를 할 경우,
            else {
                binding.discountArea.visibility = View.GONE
                binding.iceCreamTotalPrice.text = CommonUtils.makeComma(iceCream.price)
            }

            val imageUrl = ICE_CREAM_IMAGE_BASE_URL + iceCream.img

            Glide.with(binding.root).load(imageUrl).into(binding.itemIceCreamImage)

            binding.root.setOnClickListener {
                onItemClickListener(iceCream)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IceCreamViewHolder {
        val binding = ItemIcecreamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IceCreamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IceCreamViewHolder, position: Int) {
        holder.bind(iceCreamList[position])
    }

    override fun getItemCount(): Int = iceCreamList.size
}
