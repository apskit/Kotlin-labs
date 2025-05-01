package com.example.lab2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Task 3
        val mySwitch = findViewById<Switch>(R.id.switch1)
        mySwitch.setOnCheckedChangeListener { _, isChecked ->
            val intent = Intent().apply {
                putExtra("Switch", isChecked)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}
