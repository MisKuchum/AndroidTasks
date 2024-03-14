package com.example.androidtasks

import android.content.pm.PackageManager
import android.location.Location
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.location.Geocoder
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.Locale
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class ActivityC : AppCompatActivity() {
    private val TAG = "MyApp"
    private var hasLocationPermissions = false
    private lateinit var etBirthdate: EditText
    private lateinit var spBenchPress: Spinner
    private lateinit var etCity: EditText
    private lateinit var btnGetLocation: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var ivProfilePhoto: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ActivityC is [onCreate] now")

        setContentView(R.layout.activity_c)
        etBirthdate = findViewById(R.id.et_birthdate)
        spBenchPress = findViewById(R.id.sp_bench_press)
        etCity = findViewById(R.id.et_city)
        btnGetLocation = findViewById(R.id.btn_get_location)
        ivProfilePhoto = findViewById(R.id.iv_profile_photo)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

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
                var power = when ((view as TextView).text.toString()) {
                    "Сложно" -> "Хиленький"
                    "Средне" -> "Ну норм"
                    "Легко" -> "Перебор"
                    else -> "Инопланетянин какой-то"
                }

                Toast.makeText(this@ActivityC, power, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        ivProfilePhoto.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }

        firstCheckPermissions()

        val setLocationDialog = AlertDialog.Builder(this)
            .setTitle("Установка местоположения")
            .setMessage("Вы хотите установить местоположение автоматически?")
            .setIcon(R.drawable.ic_add_location)
            .setPositiveButton("Да") { _, _ ->
                setLocation()
            }
            .setNegativeButton("Нет") {_, _ ->
                Toast.makeText(this, "Тогда зачем кнопку нажимал?", Toast.LENGTH_LONG).show()
            }
            .create()

        setCurrentFragment(ProfileFragment())
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.mi_profile  -> setCurrentFragment(ProfileFragment())
                R.id.mi_work -> setCurrentFragment(WorkFragment())
                R.id.mi_hobby -> setCurrentFragment(HobbyFragment())
            }
            true
        }

        btnGetLocation.setOnClickListener {
            setLocationDialog.show()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 &&
            grantResults.isNotEmpty()) {
            if (grantResults.filter { result -> result == PackageManager.PERMISSION_GRANTED }.size == permissions.size) {
                hasLocationPermissions = true
                setLocation()
            }
            else
                Toast.makeText(applicationContext, "Нужно разрешение", Toast.LENGTH_LONG)
                    .show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MyOptionsMenu().create(this, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = MyOptionsMenu().itemSelected(this, item)

    fun onClickInputButton(view: View) {
        intent.putExtra("input", etBirthdate.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun hasAccessCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasAccessFineLocationPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        var permissionsToRequest = mutableListOf<String>()

        if (!hasAccessCoarseLocationPermission())
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!hasAccessFineLocationPermission())
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)

        Log.d(TAG, "${permissionsToRequest.toString()}")

        if (permissionsToRequest.isNotEmpty())
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        else
            hasLocationPermissions = true
    }

    private fun setLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (hasLocationPermissions) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        val geoCoder = Geocoder(this, Locale.getDefault())
                        val address = geoCoder.getFromLocation(location.latitude,location.longitude,1)
                        val cityName = address?.get(0)?.locality

                        if (cityName != null) etCity.setText(cityName)
                        else etCity.setText("Нет информации")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Не удалось получить информацию о местоположении!")
                }
        }
        else requestPermissions()
    }

    private fun firstCheckPermissions() {
        if (hasAccessCoarseLocationPermission() && hasAccessFineLocationPermission())
            hasLocationPermissions = true
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragments, fragment)
            commit()
        }
    }
}