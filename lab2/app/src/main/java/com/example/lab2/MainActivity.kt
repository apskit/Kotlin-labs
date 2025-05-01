package com.example.lab2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Task 1.
        val secondActivityIntent = Intent(this, SecondActivity::class.java)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            startActivity(Intent(secondActivityIntent))
        }

        // Task 4.
        val alarmButton = findViewById<Button>(R.id.alarmButton)
        alarmButton.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, 15)
                putExtra(AlarmClock.EXTRA_MINUTES, 45)
                putExtra(AlarmClock.EXTRA_MESSAGE, "Pobudka!")
            }

            startActivity(intent)
        }
    }
}