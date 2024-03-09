package com.example.androidtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActivityD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)

        val photoUriPath = intent.getStringExtra(PHOTO_URI_PATH) ?: ""

        val transaction = supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_first_fragment, FirstFragment())
            replace(R.id.fl_second_fragment, SecondFragment.newInstance(photoUriPath))
            commit()
        }
    }
}