package com.ssafy.icepop.ui.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.databinding.ItemRecentOrderBinding

class RecentOrderAdapter(
    var iceCreamOrderList : List<IceCreamOrder>,
    private val onItemClickListener: (IceCreamOrder) -> Unit
) : RecyclerView.Adapter<RecentOrderAdapter.RecentOrderViewHolder>() {

    inner class RecentOrderViewHolder(private val binding : ItemRecentOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(iceCreamOrder: IceCreamOrder) {

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