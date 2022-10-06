package com.example.zimozi.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 *  the room database class
 */

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotificationsDatabase : RoomDatabase() {

    abstract fun getNotificationsDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: NotificationsDatabase? = null

        fun getDatabase(context: Context): NotificationsDatabase {
            //Log.d("Alarm", "getDatabase called")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotificationsDatabase::class.java,
                    "notifications_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

