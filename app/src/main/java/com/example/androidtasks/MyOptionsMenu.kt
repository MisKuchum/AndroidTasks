package com.example.androidtasks

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MyOptionsMenu {
    fun create(activity: AppCompatActivity, menu: Menu?): Boolean {
        activity.menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    fun itemSelected(activity: AppCompatActivity, item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.mi_interesting -> Toast.makeText(activity, "Здесь могло быть ваше \"что-то интересное\"", Toast.LENGTH_SHORT).show()
            R.id.mi_settings-> Toast.makeText(activity, "Настройки", Toast.LENGTH_SHORT).show()
            R.id.mi_about -> Toast.makeText(activity, "Тестовое приложение для обучения", Toast.LENGTH_SHORT).show()
            R.id.mi_exit -> activity.finish()
        }
        return true
    }
}