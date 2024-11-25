package com.ssafy.icepop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.data.model.dto.IceCreamOrderReview
import com.ssafy.icepop.databinding.ItemOrderReviewBinding

class OrderReviewAdapter(
    var reviews : List<IceCreamOrderReview>
) : RecyclerView.Adapter<OrderReviewAdapter.OrderReviewViewHolder>() {
    inner class OrderReviewViewHolder(private val binding : ItemOrderReviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(review : IceCreamOrderReview) {
            binding.reviewContent.text = review.content
            binding.reviewRating.text = review.rate.toString()
            binding.reviewUserName.text = review.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderReviewViewHolder {
        val binding = ItemOrderReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderReviewViewHolder(binding)
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: OrderReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }
}