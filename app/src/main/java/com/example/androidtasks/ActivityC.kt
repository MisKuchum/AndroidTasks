package com.example.androidtasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast


class ActivityC : AppCompatActivity() {
    private val TAG = "MyApp"
    private lateinit var etBirthdate: EditText
    private lateinit var spBenchPress: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityC is [onCreate] now")

        setContentView(R.layout.activity_c)
        etBirthdate = findViewById<EditText>(R.id.et_birthdate)
        spBenchPress = findViewById(R.id.sp_bench_press)

        val difficultyArray = resources.getStringArray(R.array.difficulty)
        val spDifficultyAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            difficultyArray)
        spBenchPress.adapter = spDifficultyAdapter

        spBenchPress.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var power = when((view as TextView).text.toString()) {
                    "Сложно" -> "Хиленький"
                    "Средне" -> "Ну норм"
                    "Легко"  -> "Перебор"
                    else     -> "Инопланетянин какой-то"
                }

                Toast.makeText(this@ActivityC, power, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
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
        intent.putExtra("input", etBirthdate.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }


}