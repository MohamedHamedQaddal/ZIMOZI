package com.example.zimozi.alarmutils

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.zimozi.views.MainActivity
import com.example.zimozi.alarmutils.NotificationHelper.getNotificationManager
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val intentToRepeat = Intent(context, MainActivity::class.java)
        intentToRepeat.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT)
        val repeatedNotification = buildLocalNotification(context, pendingIntent).build()
        getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification)
        //repeatedNotification.setLatestEventInfo(context, from, message, contentIntent)
        getNotificationManager(context).notify(1, repeatedNotification)
        NotificationHelper.scheduleRepeatingRTCNotification(context)
        NotificationHelper.scheduleRepeatingElapsedNotification(context)
        Log.d("Alarm", "Alarm received")
    }

    private fun buildLocalNotification(context: Context?, pendingIntent: PendingIntent?): NotificationCompat.Builder {
        Log.d("Alarm", "AlarmReceiver notification built")
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        return NotificationCompat.Builder(context!!)
            .setContentTitle(context.getString(com.example.zimozi.R.string.alarmmanager_notification))
            .setContentText(context.getString(com.example.zimozi.R.string.alarmmanager_notification_text))
            .setSmallIcon(R.drawable.arrow_up_float)
            .setColor(ContextCompat.getColor(context, android.R.color.holo_orange_dark))
            .setAutoCancel(true)
            .setShowWhen(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
    }
}