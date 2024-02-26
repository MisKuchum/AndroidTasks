package com.example.androidtasks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlin.random.Random

class ActivityA : AppCompatActivity() {
    private val TAG = "MyApp"
    private lateinit var btnToast: Button
    private lateinit var etPhone: EditText
    private lateinit var etHeight: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivPhoto: ImageView
    private lateinit var etFirstName: EditText
    private lateinit var etSecondName: EditText
    private lateinit var etPatronymic: EditText
    private var toastCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityA is [onCreate] now")

        setContentView(R.layout.activity_a)
        btnToast = findViewById(R.id.btn_toast)
        etPhone = findViewById(R.id.et_phone)
        etHeight = findViewById(R.id.et_height)
        etPassword = findViewById(R.id.et_password)
        ivPhoto = findViewById(R.id.iv_photo)
        etFirstName = findViewById(R.id.et_first_name)
        etSecondName = findViewById(R.id.et_second_name)
        etPatronymic = findViewById(R.id.et_patronymic)

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

        ivPhoto.setOnClickListener {
            Toast(this).apply {
                duration = Toast.LENGTH_SHORT
                view = layoutInflater.inflate(R.layout.custom_toast, null)
                show()
            }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MyOptionsMenu().create(this, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = MyOptionsMenu().itemSelected(this, item)

    fun onClickOpenActivityB(view: View) {
        Intent (this, ActivityB::class.java).also {
            it.putExtra(FIRST_NAME, etFirstName.text.toString())
            it.putExtra(SECOND_NAME, etSecondName.text.toString())
            it.putExtra(PATRONYMIC, etPatronymic.text.toString())
            startActivity(it)
        }
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
            var haveDigit = false
            var haveUpperCase = false

            etText.forEach {
                if (!haveDigit && it.isDigit()) haveDigit = true
                else if (!haveUpperCase && it.isUpperCase()) haveUpperCase = true
                if (haveDigit && haveUpperCase) return@forEach
            }

            if (
                haveDigit &&
                haveUpperCase &&
                etText.length >= MIN_PASSWORD_LEN
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
    
    companion object {
        private const val MIN_PASSWORD_LEN = 8
    }
}

const val FIRST_NAME = "FIRST_NAME"
const val SECOND_NAME = "SECOND_NAME"
const val PATRONYMIC = "PATRONYMIC"