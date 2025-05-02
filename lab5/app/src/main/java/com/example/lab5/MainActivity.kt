package com.example.lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.lab5.databinding.ActivityMainBinding
import java.text.DecimalFormat
import kotlin.random.Random

const val RANDOM_NUMBERS = 100

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val inputArray = IntArray(RANDOM_NUMBERS)
    private var sortedArray = IntArray(RANDOM_NUMBERS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Generate numbers
        val generateNumbersButton = findViewById<Button>(R.id.generateNumbersButton)
        generateNumbersButton.setOnClickListener {
            for (i in inputArray.indices) {
                inputArray[i] = Random.nextInt(0, 99)
            }

            val displayNumbersButton = findViewById<Button>(R.id.displayInputButton)
            displayNumbersButton.visibility = View.VISIBLE

            val kotlinSortButton = findViewById<Button>(R.id.kotlinSortButton)
            kotlinSortButton.visibility = View.VISIBLE

            val cppSortButton = findViewById<Button>(R.id.cppSortButton)
            cppSortButton.visibility = View.VISIBLE
        }

        // Display input
        val displayNumbersButton = findViewById<Button>(R.id.displayInputButton)
        displayNumbersButton.visibility = View.GONE
        displayNumbersButton.setOnClickListener {
            displayNumbers(inputArray)
        }

        // C++ sorting
        val cppSortButton = findViewById<Button>(R.id.cppSortButton)
        cppSortButton.visibility = View.GONE
        cppSortButton.setOnClickListener {
            sortedArray = inputArray.copyOf()

            val startTime = System.nanoTime()
            sortArray(sortedArray)
            val elapsedTime = System.nanoTime() - startTime
            displayTime(elapsedTime)

            displayNumbers(sortedArray)
        }

        // Kotlin sorting
        val kotlinSortButton = findViewById<Button>(R.id.kotlinSortButton)
        kotlinSortButton.visibility = View.GONE
        kotlinSortButton.setOnClickListener {
            sortedArray = inputArray.copyOf()

            val startTime = System.nanoTime()
            bubbleSort(sortedArray)
            val elapsedTime = System.nanoTime() - startTime
            displayTime(elapsedTime)

            displayNumbers(sortedArray)
        }

    }

    private fun displayNumbers(array: IntArray) {
        val displayedNumbers = findViewById<TextView>(R.id.numbersText)
        var numbers = ""

        for (i in array.indices) {
            numbers += array[i]
            numbers += " "
        }

        displayedNumbers.text = numbers
    }

    private fun bubbleSort(array: IntArray){
        for (i in array.indices) {
            for (j in 0 until array.size - i - 1) {
                if (array[j] > array[j + 1]) {
                    array[j] = array[j + 1].also { array[j + 1] = array[j] }
                }
            }
        }
    }

    private fun displayTime(time: Long) {
        val executionTime = findViewById<TextView>(R.id.executionTimeText)
        val useconds : Double = time / 1000.0
        val df = DecimalFormat("#.# ps")
        executionTime.text = df.format(useconds)
    }


    /**
     * A native method that is implemented by the 'lab5' native library,
     * which is packaged with this application.
     */

    external fun sortArray(array: IntArray)

    companion object {
        // Used to load the 'lab5' library on application startup.
        init {
            System.loadLibrary("lab5")
        }
    }
}