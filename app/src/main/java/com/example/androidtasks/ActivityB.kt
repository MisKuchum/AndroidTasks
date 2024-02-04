package com.example.androidtasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class ActivityB : AppCompatActivity() {
    private val TAG = "MyApp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityB is [onCreate] now")

        setContentView(R.layout.activity_b)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ActivityB is [onStart] now")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "ActivityB is [onRestart] now")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ActivityB is [onResume] now")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ActivityB is [onPause] now")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ActivityB is [onStop] now")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ActivityB is [onDestroy] now")
    }

    fun onClickOpenActivityC(view: View) {
        val intent = Intent(this, ActivityC::class.java)
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
//            if (it.resultCode == RESULT_OK) {
//                val value = it.data?.getStringExtra("input")
//                val textView = findViewById<View>(R.id.tvActivityCInput) as TextView
//                textView.text = "Input from ActivityC: $value"
//            }
        }
}