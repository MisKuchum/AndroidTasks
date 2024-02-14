package com.example.androidtasks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kotlin.random.Random

class ActivityA : ComponentActivity() {
    private val TAG = "MyApp"
    private val minPasswordLen = 8
    private lateinit var btnToast: Button
    private lateinit var etPhone: EditText
    private lateinit var etHeight: EditText
    private lateinit var etPassword: EditText
    private var toastCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityA is [onCreate] now")

        setContentView(R.layout.activity_a)
        btnToast = findViewById(R.id.btn_toast)
        etPhone = findViewById(R.id.et_phone)
        etHeight = findViewById(R.id.et_height)
        etPassword = findViewById(R.id.et_password)

        etPhone.addTextChangedListener {
            val rnd = Random.Default
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            etPhone.setTextColor(color)
        }

        btnToast.setOnClickListener {
            Toast.makeText(applicationContext, "Съел тост: $toastCounter шт.", Toast.LENGTH_SHORT)
                .show()
            Log.d(TAG, "Съел тост: $toastCounter шт.")
            toastCounter++
        }

        addTextChangedListeners()
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

    private fun addTextChangedListeners() {
        etPhone.addTextChangedListener {
            etPhone.setTextColor(getRandomColor())
        }

        etHeight.addTextChangedListener {
            etHeight.setBackgroundColor(getRandomColor())
        }

        etPassword.addTextChangedListener {
            val etText = etPassword.text.toString()
            if (
                etText.any() { it.isDigit() } &&
                etText.any() { it.isUpperCase() } &&
                etText.length > minPasswordLen
                ) {
                btnToast.visibility = View.VISIBLE
            }
            else {
                btnToast.visibility = View.INVISIBLE
            }
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random.Default
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}