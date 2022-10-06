package com.example.zimozi

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.zimozi.databinding.NotificationItemLayoutBinding
import com.example.zimozi.model.NotificationEntity


class MainListAdapter : PagingDataAdapter<NotificationEntity, MainListAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(private val binding: NotificationItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Alarm", "onCreateViewHolder called")
        val binding = NotificationItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Alarm", "onBindViewHolder called")
        getItem(position)?.let { holder.bind(it) }
    }

    object DataDifferntiator : DiffUtil.ItemCallback<NotificationEntity>() {
        override fun areItemsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity): Boolean {
            return oldItem.notificationTime == newItem.notificationTime
        }
        override fun areContentsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity): Boolean {
            return oldItem == newItem
        }
    }
}