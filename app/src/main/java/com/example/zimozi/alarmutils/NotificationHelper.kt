package com.example.zimozi.alarmutils

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.SystemClock
import android.util.Log
import java.util.*

object NotificationHelper {

    const val ALARM_HOUR = 17
    const val ALARM_MINUTE = 30

    var ALARM_TYPE_RTC = 100
    private var rtcAlarmManager: AlarmManager? = null
    private var rtcAlarmIntent: PendingIntent? = null

    private var ALARM_TYPE_ELAPSED = 101
    private var elapsedAlarmManager: AlarmManager? = null
    private var elapsedAlarmIntent: PendingIntent? = null

    fun getNotificationManager(context: Context): NotificationManager {
        Log.d("Alarm", "getNotificationManager called")
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun scheduleRepeatingRTCNotification(context: Context) {
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, ALARM_HOUR)
            set(Calendar.MINUTE, ALARM_MINUTE)
        }
        val intent = Intent(context, AlarmReceiver::class.java)
        rtcAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        rtcAlarmIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        rtcAlarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_HOUR * 2,
            rtcAlarmIntent)
        Log.d("Alarm", "scheduleRepeatingRTCNotification called")
    }

    fun cancelAlarmRTC() {
        if (rtcAlarmManager != null) {
            rtcAlarmManager!!.cancel(rtcAlarmIntent)
        }
        Log.d("Alarm", "rtcAlarm cancelled")
    }

    fun scheduleRepeatingElapsedNotification(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        elapsedAlarmIntent = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        elapsedAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        elapsedAlarmManager!!.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            elapsedAlarmIntent
        )
        Log.d("Alarm", "scheduleRepeatingElapsedNotification called")
    }

    fun cancelAlarmElapsed() {
        if (elapsedAlarmManager != null) {
            elapsedAlarmManager!!.cancel(elapsedAlarmIntent)
        }
        Log.d("Alarm", "elapsedAlarm cancelled")
    }

    fun enableBootReceiver(context: Context) {
        val receiver = ComponentName(context, AlarmBootReceiver::class.java)
        val packageManager = context.packageManager
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
        Log.d("Alarm", "boot enabled")
    }

    fun disableBootReceiver(context: Context) {
        val receiver = ComponentName(context, AlarmBootReceiver::class.java)
        val packageManager = context.packageManager
        packageManager.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
        Log.d("Alarm", "boot disabled")
    }
}