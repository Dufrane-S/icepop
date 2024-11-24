package com.ssafy.icepop.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.data.model.dto.IceCreamOrder
import com.ssafy.icepop.databinding.ItemOrderBinding

class OrderListAdapter (
    var iceCreamOrderList : List<IceCreamOrder>,
    private val onItemClickListener : (Unit) -> Unit
) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {
    inner class OrderListViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(iceCreamOrder: IceCreamOrder) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderListViewHolder(binding)
    }

    override fun getItemCount(): Int = iceCreamOrderList.size

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bind(iceCreamOrderList[position])
    }
}