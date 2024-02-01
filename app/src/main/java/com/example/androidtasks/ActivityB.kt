package com.example.androidtasks

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
    }

    fun onClickOpenActivityC(view: View) {
        val intent = Intent(this, ActivityC::class.java)
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val value = it.data?.getStringExtra("input")
                val textView = findViewById<View>(R.id.tvActivityCInput) as TextView
                textView.text = "Input from ActivityC: $value"
            }
        }
}