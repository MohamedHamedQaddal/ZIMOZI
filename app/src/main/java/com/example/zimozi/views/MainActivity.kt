package com.example.zimozi.views

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zimozi.alarmutils.NotificationHelper
import com.example.zimozi.NotificationsAdapter
import com.example.zimozi.alarmutils.AlarmBootReceiver
import com.example.zimozi.alarmutils.AlarmReceiver
import com.example.zimozi.database.NotificationEntity
import com.example.zimozi.databinding.ActivityMainBinding
import com.example.zimozi.viewmodel.AppViewModel
import com.example.zimozi.viewmodel.AppViewModelFactory

class MainActivity : AppCompatActivity() {

    private var context: Context? = null
    private lateinit var appViewModel: AppViewModel
    private lateinit var binding: ActivityMainBinding
    //private val notificationHelper = NotificationHelper
    private val notificationsAdapter = NotificationsAdapter()

    companion object {
        const val NOTIFICATION_TITLE = "A_Title"
        const val NOTIFICATION_MESSAGE = "A_Message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Log.d("Alarm", "Activity created")

        scheduleAlarm()

        context = applicationContext

        appViewModel = ViewModelProvider(this, AppViewModelFactory(application))[AppViewModel::class.java]

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.adapter = notificationsAdapter

        appViewModel.fireNotifications()

        appViewModel.getAllNotes().observe(this, Observer {
            it?.let {
                //notificationsAdapter.submitList(it?.toMutableList())
            }
        })
    }

//    fun clickToggleButtonRTC(view: View) {
//        val hours = "22"
//        val minutes = "5"
//        val isEnabled = (view as ToggleButton).isEnabled
//        if (isEnabled) {
//            context?.let { scheduleRepeatingRTCNotification(it, hours, minutes) }
//            context?.let { notificationHelper.enableBootReceiver(it) }
//        } else {
//            notificationHelper.cancelAlarmRTC()
//            context?.let { notificationHelper.disableBootReceiver(it) }
//        }
//    }

//    fun clickToggleButtonElapsed(view: View) {
//        val isEnabled = (view as ToggleButton).isEnabled
//        if (isEnabled) {
//            context?.let { notificationHelper.scheduleRepeatingElapsedNotification(it) }
//            context?.let { notificationHelper.enableBootReceiver(it) }
//        } else {
//            notificationHelper.cancelAlarmElapsed()
//            context?.let { notificationHelper.disableBootReceiver(it) }
//        }
//    }

//    fun cancelAlarms(view: View?) {
//        notificationHelper.cancelAlarmRTC()
//        notificationHelper.cancelAlarmElapsed()
//        context?.let { notificationHelper.disableBootReceiver(it) }
//    }

    private fun scheduleAlarm() {
        //Log.d("Alarm", "scheduleAlarm called")
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val alarmIntent = Intent(this, AlarmBootReceiver::class.java)
        alarmIntent.putExtra("alarm_title",NOTIFICATION_TITLE)
        alarmIntent.putExtra("alarm_message",NOTIFICATION_MESSAGE)
        val pendingIntent = PendingIntent.getBroadcast(this.applicationContext, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val calendar  = Calendar.getInstance()
        //calendar.set(Calendar.HOUR_OF_DAY, 8)
        //calendar.set(Calendar.MINUTE, 0)
        //calendar.set(Calendar.SECOND, 0)
        calendar.add(Calendar.SECOND, 5)
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        //alarmManager!!.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        //alarmManager!![AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + i * 1000] = pendingIntent
        //Toast.makeText(this, "Alarm set in $i seconds", Toast.LENGTH_LONG).show()
    }
}