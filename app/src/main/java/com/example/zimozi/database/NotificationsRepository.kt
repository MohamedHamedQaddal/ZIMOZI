package com.example.zimozi.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.zimozi.database.NotificationDao
import com.example.zimozi.database.NotificationEntity

/**
 *  a wrapper class for Room database CRUD operations
 */

class NotificationsRepository(private val notificationDao: NotificationDao) {

    val allNotifications: LiveData<List<NotificationEntity>> = notificationDao.getAllNotifications()

    suspend fun insert(notification: NotificationEntity) {
        Log.d("Alarm", "insert in repo called")
        notificationDao.insert(notification)
    }
}