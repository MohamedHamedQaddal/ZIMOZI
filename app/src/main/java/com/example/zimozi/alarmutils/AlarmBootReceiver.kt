package com.example.zimozi.alarmutils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED){
            if (context != null) {
                Log.d("Alarm", "Alarm received")
                NotificationHelper.scheduleRepeatingRTCNotification(context)
                NotificationHelper.scheduleRepeatingElapsedNotification(context)
            } else {
                Log.d("Alarm", "Alarm not received")
            }
        }
    }
}