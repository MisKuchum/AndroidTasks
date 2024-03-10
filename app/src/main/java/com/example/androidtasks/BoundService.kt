package com.example.androidtasks

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.app.Service
import android.util.Log

class BoundService : Service() {
    private val TAG = "ServiceLogger"
    private val binder = BoundBinder()

    inner class BoundBinder : Binder() {
        fun getService(): BoundService {
            return this@BoundService
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "BoundService is [onBind] now")
        return binder
    }

    override fun onCreate() {
        Log.d(TAG, "BoundService is [onCreate] now")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "BoundService is [onStartCommand] now")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(TAG, "BoundService is [onDestroy] now")
        super.onDestroy()
    }
}