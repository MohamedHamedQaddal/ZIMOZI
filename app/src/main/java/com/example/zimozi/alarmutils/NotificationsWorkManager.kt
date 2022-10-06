package com.example.zimozi.alarmutils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.zimozi.R
import com.example.zimozi.views.MainActivity
import kotlinx.coroutines.*
import kotlin.random.Random


@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class NotificationsWorkManager(private val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            showNotification()
            NotificationHelper.scheduleRepeatingRTCNotification(context)
            NotificationHelper.scheduleRepeatingElapsedNotification(context)
            Log.d("Alarm", "WorkManager works successfully")
            Result.success()
        } catch (throwable: Throwable) {
            Log.d("Alarm", "WorkManager failed")
            Result.failure()
        }
    }

    @SuppressLint("StringFormatInvalid", "UnspecifiedImmutableFlag")
    private fun showNotification() {
        Log.d("Alarm", "showNotification called")
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = context.getString(R.string.default_notification_channel_id)
        val channelName = context.getString(R.string.default_notification_channel_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(context.getString(R.string.workmanager_notification))
            .setContentText(context.getString(R.string.workmanager_notification_text))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            .setAutoCancel(true)
            .setShowWhen(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notificationManager.notify(Random.nextInt(), notificationBuilder.build())
    }
}