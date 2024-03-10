package com.example.androidtasks

import android.app.IntentService
import android.content.Intent
import android.util.Log

class CounterService: IntentService("CounterService") {
    private var count = 0

    init {
        instance = this
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            isRunning = true
            val counterIntent = Intent(COUNTER_CHANGED)
            while (isRunning) {
                increaseCount(counterIntent)
                Thread.sleep(1000)
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CounterServiceLogger", "Service stopped")
        isRunning = false
        instance.stopSelf()
    }

    private fun increaseCount(intent: Intent) {
        Log.d("CounterServiceLogger", "Service is running... Now is $count")
        intent.putExtra(COUNT, count)
        sendBroadcast(intent)
        count++
    }

    companion object {
        private lateinit var instance: CounterService
        var isRunning = false
    }
}