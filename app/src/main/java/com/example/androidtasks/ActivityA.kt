package com.example.androidtasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity

class ActivityA : ComponentActivity() {
    private val TAG = "MyApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityA is [onCreate] now")

        setContentView(R.layout.activity_a)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ActivityA is [onStart] now")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "ActivityA is [onRestart] now")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ActivityA is [onResume] now")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ActivityA is [onPause] now")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ActivityA is [onStop] now")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ActivityA is [onDestroy] now")
    }

    fun onClickOpenActivityB(view: View) {
        val intent = Intent(this, ActivityB::class.java)
        startActivity(intent)
    }
}