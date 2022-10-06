package com.example.zimozi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.*
import com.example.zimozi.database.NotificationEntity
import com.example.zimozi.database.NotificationsDatabase
import com.example.zimozi.database.NotificationsRepository
import com.example.zimozi.alarmutils.NotificationsWorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class AppViewModel(application: Application): ViewModel() {

    private var repository: NotificationsRepository
    private val workManager = WorkManager.getInstance(application)
    private val _notifications = MutableLiveData<List<NotificationEntity>>()
    private var notifications: LiveData<List<NotificationEntity>>
    //get() = _notifications

    init {
            val dao = NotificationsDatabase.getDatabase(application).getNotificationsDao()
            repository = NotificationsRepository(dao)
            notifications = repository.allNotifications
    }

//    companion object {
//        const val ITEMS_PER_PAGE = 10
//    }
//
//    val notificationItems: Flow<PagingData<Article>> = Pager(
//        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
//        pagingSourceFactory = { repository.articlePagingSource() }
//    )
//        .flow
//        .cachedIn(viewModelScope)

    @OptIn(InternalCoroutinesApi::class)
    internal fun fireNotifications() {

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(false)
            .build()

        val firePeriodicNotifications = PeriodicWorkRequest
            .Builder(NotificationsWorkManager::class.java,15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "periodicFiring",
            ExistingPeriodicWorkPolicy.KEEP,
            firePeriodicNotifications
        )
    }

        fun insertNote(notification: NotificationEntity) = viewModelScope.launch(Dispatchers.IO) {
            Log.d("Alarm", "addNote in viewModel called")
            repository.insert(notification)
        }

        fun getAllNotes(): LiveData<List<NotificationEntity>> {
            //Log.d("Alarm", "getAllNotes in viewModel called")
            return repository.allNotifications
        }
}