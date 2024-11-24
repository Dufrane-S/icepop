package com.ssafy.icepop.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.databinding.ItemOrderDetailBinding

class OrderDetailListAdapter (
    var iceCreamOrderDetails : List<Unit>,
    private val onItemClickListener : (Unit) -> Unit
) : RecyclerView.Adapter<OrderDetailListAdapter.OrderDetailViewHolder>(){
    inner class OrderDetailViewHolder(private val binding : ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(iceCreamOrderDetail : Unit) {}
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