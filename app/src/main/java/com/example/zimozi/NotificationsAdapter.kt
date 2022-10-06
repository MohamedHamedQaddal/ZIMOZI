package com.example.zimozi

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zimozi.databinding.NotificationItemLayoutBinding
import com.example.zimozi.model.NotificationEntity

class NotificationsAdapter : ListAdapter<NotificationEntity, NotificationsAdapter.NotificationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        Log.d("Alarm", "onCreateViewHolder called")
        val binding = NotificationItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        Log.d("Alarm", "onBindViewHolder called")
        holder.bind(getItem(position))
    }

    class NotificationViewHolder(private val binding: NotificationItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(notification: NotificationEntity) {
            Log.d("Alarm", "NotificationViewHolder.bind called")
            binding.apply {
                notifBodyId.text = notification.notificationHead
                notifDateId.text = notification.notificationDate
                notifTimeId.text = notification.notificationTime
                notifBatteryId.text = notification.notificationBatteryPercentage
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NotificationEntity>() {
        override fun areItemsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity) =
            oldItem.notificationTime == newItem.notificationTime
        override fun areContentsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity) =
            oldItem == newItem
    }
}