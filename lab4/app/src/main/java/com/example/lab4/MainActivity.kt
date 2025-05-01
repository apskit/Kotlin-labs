package com.example.lab4

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private var elapsedTimeJob: Job? = null
    private var isBounded = false

    private lateinit var mService: MyService
    private var mBound: Boolean = false
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName,
                                        service:
                                        IBinder
        ) {
            val binder = service as MyService.LocalBinder
            mService = binder.getService()
            mBound = true
            askServiceAbtItsTime()
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bindingButton = findViewById<Button>(R.id.bindingButton)
        bindingButton.setOnClickListener {
            if (bindingButton.text == "Start") {
                val intent = Intent(this, MyService::class.java)
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                bindingButton.text = "Stop"
                isBounded = true
            }
            else {
                unbindService(serviceConnection)
                bindingButton.text = "Start"
                isBounded = false

                val counterText = findViewById<TextView>(R.id.counter)
                "Click to bind to MyService".also { counterText.text = it }
            }

        }

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_DENIED
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                arrayOf(POST_NOTIFICATIONS), 0)
        }

    }

    private fun askServiceAbtItsTime() {
        val counterText = findViewById<TextView>(R.id.counter)
        elapsedTimeJob?.cancel()
        elapsedTimeJob = lifecycleScope.launch {
            while (isBounded) {
                val time = mService.getRunningTime()
                val seconds : Double = time / 1000.0
                val df = DecimalFormat("#.#")
                counterText.text = df.format(seconds)
                delay(100)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Notifications Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this@MainActivity, "Notifications Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}