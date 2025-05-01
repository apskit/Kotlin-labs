package com.example.lab1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        // Task 4.
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 5
        progressBar.progress = 0

        // Task 1.
        val startButton = findViewById<Button>(R.id.button)
        val helloTextView: TextView = findViewById(R.id.text)
        startButton.setOnClickListener {
            helloTextView.text = "Click!";

            // Task 2.
            val job = lifecycleScope.launch {
                Log.d("YourTAG", "Job started")
                //delay(5000)

                // Task 3.
                for (i in 1..5) {
                    delay(1000)
                    launch(Dispatchers.Main) {
                        helloTextView.text = "Current progress: $i/5"

                        progressBar.progress = i
                    }
                }

                launch(Dispatchers.Main) {
                    helloTextView.text = "Done!";
                }
            }
        }

    }
}