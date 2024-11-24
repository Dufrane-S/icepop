package com.ssafy.icepop.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.icepop.data.model.dto.NotificationItem
import com.ssafy.icepop.databinding.ItemNotificationBinding

class NotificationAdapter(
    var notificationItems : List<NotificationItem>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    inner class NotificationViewHolder(private val binding : ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notificationItem: NotificationItem) {
            if (notificationItem.type == 1) {
                binding.notificationContent.text = "주문이 접수되었습니다.(주문번호 ${notificationItem.orderId})"
            }
            else {
                binding.notificationContent.text = "주문번호 ${notificationItem.orderId}번 준비 완료되었습니다."
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun getItemCount(): Int = notificationItems.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notificationItems[position])
    }
}