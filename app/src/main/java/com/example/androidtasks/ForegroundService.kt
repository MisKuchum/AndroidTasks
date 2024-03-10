package com.example.androidtasks

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

class ForegroundService : Service() {
    private val TAG = "ServiceLogger"
    private lateinit var notification: Notification
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "ForegroundService is [onStartCommand] now")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            notification = Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Сервис запущен в фоновом режиме")
                .setSmallIcon(R.drawable.ic_notification)
                .build()
        }

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        Log.d(TAG, "ForegroundService is [onCreate] now")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG, "ForegroundService is [onDestroy] now")
        super.onDestroy()
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {

            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}

const val CHANNEL_ID = "CHANNEL_ID"
const val CHANNEL_NAME = "CHANNEL_NAME"