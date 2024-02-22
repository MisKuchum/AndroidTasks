package com.example.androidtasks

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kotlin.random.Random

class ActivityA : ComponentActivity() {
    private val TAG = "MyApp"
    private lateinit var btnToast: Button
    private lateinit var etPhone: EditText
    private lateinit var etHeight: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivPhoto: ImageView
    private var toastCounter = 0
    private var hasAllRequiredPermissions = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityA is [onCreate] now")

        setContentView(R.layout.activity_a)
        btnToast = findViewById(R.id.btn_toast)
        etPhone = findViewById(R.id.et_phone)
        etHeight = findViewById(R.id.et_height)
        etPassword = findViewById(R.id.et_password)
        ivPhoto = findViewById(R.id.iv_photo)

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

        firstCheckPermissions()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 &&
            grantResults.isNotEmpty()) {
            if (grantResults.filter { result -> result == PackageManager.PERMISSION_GRANTED }.size == permissions.size) {
                hasAllRequiredPermissions = true
                onClickOpenActivityB(null)
            }
            else
                Toast.makeText(applicationContext, "Для перехода на ActivityB необходимо принять все разрешения", Toast.LENGTH_LONG)
                    .show()
        }
    }

    fun onClickOpenActivityB(view: View?) {
        if (hasAllRequiredPermissions) {
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        } else {
            requestPermissions()
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
            if (btnToast.isVisible) btnToast.visibility = View.INVISIBLE
            else btnToast.visibility = View.VISIBLE
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random.Default
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun hasAccessCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


    private fun requestPermissions() {
        var permissionsToRequest = mutableListOf<String>()

        if (!hasAccessCoarseLocationPermission())
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!hasReadExternalStoragePermission())
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        Log.d(TAG, "${permissionsToRequest.toString()}")

        if (permissionsToRequest.isNotEmpty())
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        else
            hasAllRequiredPermissions = true
    }

    private fun firstCheckPermissions() {
        if (hasAccessCoarseLocationPermission() && hasReadExternalStoragePermission())
            hasAllRequiredPermissions = true
    }
}