package com.example.androidtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText


class ActivityC : AppCompatActivity() {
    private val TAG = "MyApp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityC is [onCreate] now")

        setContentView(R.layout.activity_c)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ActivityC is [onStart] now")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "ActivityC is [onRestart] now")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ActivityC is [onResume] now")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ActivityC is [onPause] now")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ActivityC is [onStop] now")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ActivityC is [onDestroy] now")
    }

    fun onClickInputButton(view: View) {
        val edit = findViewById<View>(R.id.etInput) as EditText
        intent.putExtra("input", edit.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }


}