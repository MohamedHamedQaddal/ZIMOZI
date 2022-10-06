package com.example.zimozi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppViewModelFactory (private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //Log.d("Alarm", "create in viewModelFactory created")
        return if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            AppViewModel(this.application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}