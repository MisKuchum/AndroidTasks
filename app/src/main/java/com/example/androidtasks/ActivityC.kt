package com.example.androidtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText


class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
    }

    fun onClickInputButton(view: View) {
        val edit = findViewById<View>(R.id.etInput) as EditText
        intent.putExtra("input", edit.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }


}