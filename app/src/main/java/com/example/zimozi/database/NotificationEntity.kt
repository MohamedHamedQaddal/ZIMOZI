package com.example.zimozi.database

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/**
 *  represents the notification that is to be saved to the local database
 */

@Entity(tableName = "notifications_table")
data class NotificationEntity (
    @ColumnInfo(name = "head")
    val notificationHead :String?,
    @ColumnInfo(name = "body")
    val notificationBody :String?,
    @ColumnInfo(name = "time")
    val notificationTime :String?,
    @ColumnInfo(name = "date")
    val notificationDate :String?,
    @ColumnInfo(name = "battery_level")
    val notificationBatteryLevel: String?
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
}