package com.example.zimozi.database

import androidx.room.TypeConverter
import java.util.Calendar

class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long =
        calendar.timeInMillis

    //    @TypeConverter
    //    fun datestampToCalendar(value: Long): Calendar =
    //        Calendar.getInstance().apply { timeInMillis = value }
    //
    //    @TypeConverter
    //     fun getDateTime(s: String): String {
    //        try {
    //            val sdf = SimpleDateFormat("MM/dd/yyyy")
    //            val netDate = Date(Long.parseLong(s) * 1000)
    //            return sdf.format(netDate)
    //        } catch (e: Exception) {
    //            return e.toString()
    //        }
    //    }
}