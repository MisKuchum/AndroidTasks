package com.example.androidtasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class ActivityB : AppCompatActivity() {
    private val TAG = "MyApp"
    private lateinit var actionCTextView: TextView
    private lateinit var greenTextView: TextView
    private lateinit var redTextView: TextView
    private lateinit var blueTextView: TextView
    private lateinit var orangeTextView: TextView
    private lateinit var purpleTextView: TextView
    private lateinit var firstNameTextView: TextView
    private lateinit var secondNameTextView: TextView
    private lateinit var patronymicTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityB is [onCreate] now")

        setContentView(R.layout.activity_b)
        initViews()
        setTvOnClickListeners()
        setFioFromActivityA()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MyOptionsMenu().create(this, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = MyOptionsMenu().itemSelected(this, item)

    fun onClickOpenActivityC() {
        val intent = Intent(this, ActivityC::class.java)
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val value = it.data?.getStringExtra("input")
                actionCTextView.text = "Input from ActivityC: $value"
            }
        }

    private fun initViews() {
        actionCTextView = findViewById(R.id.tv_action_c_text)
        greenTextView = findViewById(R.id.tv_green)
        redTextView = findViewById(R.id.tv_red)
        blueTextView = findViewById(R.id.tv_blue)
        orangeTextView = findViewById(R.id.tv_orange)
        purpleTextView = findViewById(R.id.tv_purple)
        firstNameTextView = findViewById(R.id.tv_first_name)
        secondNameTextView = findViewById(R.id.tv_second_name)
        patronymicTextView = findViewById(R.id.tv_patronymic)
    }

    private fun setTvOnClickListeners() {
        val colorTvList = listOf(
            greenTextView,
            redTextView,
            orangeTextView,
            blueTextView,
            purpleTextView
        )

        colorTvList.forEach { tv ->
            tv.setOnClickListener {
                Toast.makeText(this, tv.tag.toString(), Toast.LENGTH_SHORT).show()
                Log.d(TAG, tv.tag.toString())
            }
        }
    }

    private fun setFioFromActivityA() {
        val firstName = intent.getStringExtra(FIRST_NAME)
        val secondName = intent.getStringExtra(SECOND_NAME)
        val patronymic = intent.getStringExtra(PATRONYMIC)

        if (firstName != "") firstNameTextView.text = firstName
        if (secondName != "") secondNameTextView.text = secondName
        if (patronymic != "") patronymicTextView.text = patronymic
    }
}