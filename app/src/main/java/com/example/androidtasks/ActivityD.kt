package com.example.androidtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

class ActivityD : AppCompatActivity() {
    private val dataModel: DataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)
        dataModel.photoMessage.value = intent.getStringExtra(PHOTO_URI_PATH)
    }
}