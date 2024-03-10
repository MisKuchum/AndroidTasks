package com.example.androidtasks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
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
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var tvCounterNum: TextView
    private lateinit var filter: IntentFilter

    private val counterReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == COUNTER_CHANGED) {
                val count = intent.getIntExtra(COUNT, 0)
                tvCounterNum.text = count.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityB is [onCreate] now")

        setContentView(R.layout.activity_b)
        initViews()
        setTvOnClickListeners()
        setFioFromActivityA()
        setCounterButtonOnClickListeners()

        filter = IntentFilter(COUNTER_CHANGED)
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
        registerReceiver(counterReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ActivityB is [onPause] now")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ActivityB is [onStop] now")
        unregisterReceiver(counterReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ActivityB is [onDestroy] now")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MyOptionsMenu().create(this, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = MyOptionsMenu().itemSelected(this, item)

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
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        tvCounterNum = findViewById(R.id.tv_counter_num)
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

    private fun setCounterButtonOnClickListeners() {
        btnStart.setOnClickListener {
            Intent(this, CounterService::class.java).also {
                startService(it)
            }
        }
        btnStop.setOnClickListener {
            Intent(this, CounterService::class.java).also {
                stopService(it)
            }
        }
    }
}

const val COUNT = "COUNT"
const val COUNTER_CHANGED = "COUNTER_CHANGED"