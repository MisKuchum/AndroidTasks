package com.example.androidtasks

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlin.random.Random

class ActivityA : AppCompatActivity() {
    private val TAG = "MyApp"
    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "onCreate/onAppBackgrounded"
    private val NOTIFICATION_ID = 0

    private lateinit var btnToast: Button
    private lateinit var etPhone: EditText
    private lateinit var etHeight: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivPhoto: ImageView
    private lateinit var etFirstName: EditText
    private lateinit var etSecondName: EditText
    private lateinit var etPatronymic: EditText
    private lateinit var rvFriends: RecyclerView
    private lateinit var tvActivityName: TextView
    private lateinit var flProfileButton: FrameLayout
    private lateinit var etBirthdate: EditText
    private lateinit var btnSave: Button
    private lateinit var btnLoad: Button
    private lateinit var spGender: Spinner
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private var hasAllRequiredPermissions = false
    private var toastCounter = 1
    private var hasNotificationsPermission = false
    private var isStartNewActivity = false

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
        rvFriends = findViewById(R.id.rv_friends)
        tvActivityName = findViewById(R.id.tv_activity_name)
        flProfileButton = findViewById(R.id.fl_profile_button)
        etBirthdate = findViewById(R.id.et_birthdate)
        btnSave = findViewById(R.id.btn_save)
        btnLoad = findViewById(R.id.btn_load)
        spGender = findViewById(R.id.sp_gender)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

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
            if (tvActivityName.text == "Мой профиль") {
                Toast(this).apply {
                    duration = Toast.LENGTH_SHORT
                    view = layoutInflater.inflate(R.layout.custom_toast, null)
                    show()
                }
            }
        }

        addTextChangedListeners()

        firstCheckPermissions()

        var btnProfile = Button(this)
        btnProfile.text = "Профиль"

        rvFriends.layoutManager = LinearLayoutManager(this)

        val friendsAdapter = FriendsRecyclerViewAdapter(getFriendsList())

        friendsAdapter.setOnClickListener { _, friend ->
            changeProfileValues(friend)
            if (tvActivityName.text == "Мой профиль") {
                flProfileButton.addView(btnProfile)
                tvActivityName.text = "Друг"
            }
        }

        rvFriends.adapter = friendsAdapter

        btnProfile.setOnClickListener {
            changeProfileValues(
                FriendItem("Михаил", "Кучумов", "Юрьевич", R.drawable.my_photo),
            )
            flProfileButton.removeView(btnProfile)
            tvActivityName.text = "Мой профиль"
        }

        val itemDecorator = DividerItemDecoration(rvFriends.context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                rvFriends.context,
                R.drawable.divider
            )!!
        )

        rvFriends.addItemDecoration(itemDecorator)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            val intent = when (it.itemId) {
                R.id.mi_activity_a -> Intent(this, ActivityA::class.java)
                R.id.mi_activity_b -> Intent(this, ActivityB::class.java)
                R.id.mi_activity_c -> Intent(this, ActivityC::class.java)
                R.id.mi_activity_d -> Intent(this, ActivityD::class.java)
                else -> Intent(this, ActivityA::class.java)
            }
            isStartNewActivity = true
            startActivity(intent)
            true
        }

        createNotificationChannel()
        postNotification(NotificationMode.ON_START)

        val sharedPref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        btnSave.setOnClickListener {
            editor.apply {
                putString(FIRST_NAME, etFirstName.text.toString())
                putString(SECOND_NAME, etSecondName.text.toString())
                putString(PATRONYMIC, etPatronymic.text.toString())
                putString(BIRTHDATE, etBirthdate.text.toString())
                putString(GENDER, spGender.selectedItem.toString())
                putString(PHONE, etPhone.text.toString())
                putString(HEIGHT, etHeight.text.toString())
                putString(PASSWORD, etPassword.text.toString())
                apply()
            }
        }

        btnLoad.setOnClickListener {
            val currentGender = sharedPref.getString(GENDER, null)
            val genders = resources.getStringArray(R.array.genders)

            for (i in genders.indices) {
                if (genders[i] == currentGender) {
                    spGender.setSelection(i)
                    break
                }
            }

            etFirstName.setText(sharedPref.getString(FIRST_NAME, null))
            etSecondName.setText(sharedPref.getString(SECOND_NAME, null))
            etPatronymic.setText(sharedPref.getString(PATRONYMIC, null))
            etBirthdate.setText(sharedPref.getString(BIRTHDATE, null))
            etPhone.setText(sharedPref.getString(PHONE, null))
            etHeight.setText(sharedPref.getString(HEIGHT, null))
            etPassword.setText(sharedPref.getString(PASSWORD, null))
        }
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
        isStartNewActivity = false
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ActivityA is [onPause] now")
        if (!isStartNewActivity)
            postNotification(NotificationMode.ON_APP_BACKGROUNDED)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return MyOptionsMenu().itemSelected(this, item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 &&
            grantResults.isNotEmpty()
        ) {
            if (grantResults.filter { result -> result == PackageManager.PERMISSION_GRANTED }.size == permissions.size) {
                hasAllRequiredPermissions = true
                onClickOpenActivityB(null)
            } else
                Toast.makeText(
                    applicationContext,
                    "Для перехода на ActivityB необходимо принять все разрешения",
                    Toast.LENGTH_LONG
                )
                    .show()
        }

        if (requestCode == 1 &&
            grantResults.isNotEmpty()
        ) {
            if (grantResults.all{ result -> result == PackageManager.PERMISSION_GRANTED }) {
                hasNotificationsPermission = true
            }
            else
                Log.d(TAG, "Нет разрешения на уведомления")
        }
    }

    fun onClickOpenActivityB(view: View?) {
        if (hasAllRequiredPermissions) {
            isStartNewActivity = true
            Intent(this, ActivityB::class.java).also {
                it.putExtra(FIRST_NAME, etFirstName.text.toString())
                it.putExtra(SECOND_NAME, etSecondName.text.toString())
                it.putExtra(PATRONYMIC, etPatronymic.text.toString())
                startActivity(it)
            }
        } else {
            requestPermissions()
        }
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT).apply {

            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
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
            } else {
                btnToast.visibility = View.INVISIBLE
            }
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random.Default
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun hasAccessCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun hasPostNotificationsPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

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

    private fun getFriendsList(): List<FriendItem> {
        return listOf(
            FriendItem("Максим", "Соколов", "Батькович", R.drawable.maks),
            FriendItem("Дима", "Трифонов", "Батькович", R.drawable.dima),
            FriendItem("Илья", "Гущин", "Батькович", R.drawable.ilya),
            FriendItem("Саша", "Сашин", "Александрович", R.drawable.photo_template),
            FriendItem("Коля", "Николаенко", "Николаевич", R.drawable.photo_template),
            FriendItem("Ваня", "Иванов", "Иванович", R.drawable.photo_template),
            FriendItem("Леша", "Алексеев", "Алексеевич", R.drawable.photo_template),
            FriendItem("Данил", "Данилов", "Данилович", R.drawable.photo_template),
            FriendItem("Антон", "Антонов", "Антонович", R.drawable.photo_template),
            FriendItem("Серёжа", "Сергеев", "Сергеевич", R.drawable.photo_template),
            FriendItem("Сёма", "Семёнов", "Семёнович", R.drawable.photo_template),
            FriendItem("Никита", "Никитин", "Никитич", R.drawable.photo_template),
        )
    }

    private fun changeProfileValues(newProfile: FriendItem) {
        etFirstName.setText(newProfile.firstName)
        etSecondName.setText(newProfile.secondName)
        etPatronymic.setText(newProfile.patronymic)
        ivPhoto.setImageResource(newProfile.photo)
    }

    private fun requestPostNotificationPermission() {
        if (!hasPostNotificationsPermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }
        else
            hasNotificationsPermission = true
    }

    private fun postNotification(notificationMode: NotificationMode) {
        val notificationText = when(notificationMode) {
            NotificationMode.ON_START -> "Приложение запущено"
            NotificationMode.ON_APP_BACKGROUNDED -> "Приложение свернуто"
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Важное уведомление")
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        requestPostNotificationPermission()
        if (hasNotificationsPermission)
            notificationManager.notify(NOTIFICATION_ID, notification)
        else
            requestPostNotificationPermission()
    }

    companion object {
        private const val MIN_PASSWORD_LEN = 8
        private const val MY_PREF = "my_pref"
    }
}

const val FIRST_NAME = "FIRST_NAME"
const val SECOND_NAME = "SECOND_NAME"
const val PATRONYMIC = "PATRONYMIC"
const val BIRTHDATE = "BIRTHDATE"
const val GENDER = "GENDER"
const val PHONE = "PHONE"
const val HEIGHT = "HEIGHT"
const val PASSWORD = "PASSWORD"