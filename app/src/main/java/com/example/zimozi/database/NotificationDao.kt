package com.example.zimozi.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *  represents an interface that handles database CRUD operations
 */

@Dao
interface NotificationDao {

    @Query("Select * from notifications_table order by id ASC")
    fun getAllNotifications(): LiveData<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notification : NotificationEntity)
}
