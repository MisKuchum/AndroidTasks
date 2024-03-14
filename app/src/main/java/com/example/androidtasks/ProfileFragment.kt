package com.example.androidtasks

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class ProfileFragment : Fragment() {
    private var hasLocationPermissions = false
    private val TAG = "MyApp"

    private lateinit var etBirthdate: EditText
    private lateinit var spBenchPress: Spinner
    private lateinit var etCity: EditText
    private lateinit var btnGetLocation: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etBirthdate = view.findViewById<EditText>(R.id.et_birthdate)
        spBenchPress = view.findViewById(R.id.sp_bench_press)
        etCity = view.findViewById(R.id.et_city)
        btnGetLocation = view.findViewById(R.id.btn_get_location)

        val difficultyArray = resources.getStringArray(R.array.difficulty)
        val spDifficultyAdapter = ArrayAdapter(
            requireContext(),
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

                Toast.makeText(requireContext(), power, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        firstCheckPermissions()

        val setLocationDialog = AlertDialog.Builder(requireContext())
            .setTitle("Установка местоположения")
            .setMessage("Вы хотите установить местоположение автоматически?")
            .setIcon(R.drawable.ic_add_location)
            .setPositiveButton("Да") { _, _ ->
                setLocation()
            }
            .setNegativeButton("Нет") {_, _ ->
                Toast.makeText(requireContext(), "Тогда зачем кнопку нажимал?", Toast.LENGTH_LONG).show()
            }
            .create()

        btnGetLocation.setOnClickListener {
            setLocationDialog.show()
        }
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
                Toast.makeText(requireContext(), "Нужно разрешение", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun hasAccessCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasAccessFineLocationPermission() =
        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        var permissionsToRequest = mutableListOf<String>()

        if (!hasAccessCoarseLocationPermission())
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (!hasAccessFineLocationPermission())
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)

        Log.d(TAG, "${permissionsToRequest.toString()}")

        if (permissionsToRequest.isNotEmpty())
            requestPermissions(permissionsToRequest.toTypedArray(), 0)
        else
            hasLocationPermissions = true
    }

    private fun setLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (hasLocationPermissions) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
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
}