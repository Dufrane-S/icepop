package com.ssafy.icepop.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.icepop.databinding.ItemIcecreamBinding
import com.ssafy.icepop.data.model.dto.IceCream
import com.ssafy.smartstore_jetpack.base.ApplicationClass.Companion.ICE_CREAM_IMAGE_BASE_URL

class IceCreamAdapter(
    var iceCreamList: List<IceCream>,
    private val onItemClickListener: (IceCream) -> Unit
) : RecyclerView.Adapter<IceCreamAdapter.IceCreamViewHolder>() {

    inner class IceCreamViewHolder(private val binding: ItemIcecreamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(iceCream: IceCream) {
            binding.iceCreamName.text = iceCream.name
            binding.iceCreamPrice.text = "${iceCream.price}원"

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
