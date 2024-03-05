package com.example.androidtasks

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class ActivityC : AppCompatActivity() {
    private val TAG = "MyApp"

    private lateinit var ivProfilePhoto: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityC is [onCreate] now")

        setContentView(R.layout.activity_c)

        ivProfilePhoto = findViewById(R.id.iv_profile_photo)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
            
        ivProfilePhoto.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }

        setCurrentFragment(ProfileFragment())
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.mi_profile  -> setCurrentFragment(ProfileFragment())
                R.id.mi_work -> setCurrentFragment(WorkFragment())
                R.id.mi_hobby -> setCurrentFragment(HobbyFragment())
            }
            true
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 0) {
            val uri = data?.data
            ivProfilePhoto.setImageURI(uri)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MyOptionsMenu().create(this, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = MyOptionsMenu().itemSelected(this, item)

    fun onClickInputButton(view: View) {
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragments, fragment)
            commit()
        }
    }
}