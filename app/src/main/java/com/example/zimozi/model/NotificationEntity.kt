package com.example.zimozi.model

/**
 *  represents the notification "pojo" model
    it got a title (should have a text too .. maybe concatenated)
    it got a time and date (perhaps a timestamp is better .. consider it)
    it should contain the battery level at the time of firing
  */

data class NotificationEntity (
    val notificationHead: String?,
    val notificationBody: String?,
    val notificationTime: String?,
    val notificationDate: String?,
    val notificationBatteryPercentage: String?
)