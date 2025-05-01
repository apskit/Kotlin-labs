package com.example.lab3

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ksg.mso.filehelp.FilesHelper

class EditorActivity : AppCompatActivity() {
    private val fileHelper = FilesHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText = findViewById<EditText>(R.id.editText)

        // Load file name
        val fileName = intent.getStringExtra("FILE_NAME")

        // Load text from the file
        if (fileName != null) {
            editText.setText(java.lang.String(loadFileText(fileName)))
        }

        // Save file
        val saveFileButton = findViewById<Button>(R.id.buttonSave)
        saveFileButton.setOnClickListener {
            val file = fileName?.let { it1 -> fileHelper.preparePrivateFile(this, it1) }
            if (file != null) {
                fileHelper.writeToPrivateFile(file, editText.text.toString().toByteArray())
            }
        }

        // Save file as new dialog
        val saveFileAsNewButton = findViewById<Button>(R.id.buttonSaveAsNew)
        saveFileAsNewButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter file name")

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("Save") { _, _ ->
                val newFileName = input.text.toString().trim()
                if (newFileName.isNotEmpty()) {
                    val file = fileHelper.preparePrivateFile(this, "$newFileName.txt")
                    fileHelper.writeToPrivateFile(file, editText.text.toString().toByteArray())

                    finish()
                }
            }

            builder.setNegativeButton("Cancel", null)
            builder.show()
        }
    }

    private fun loadFileText(fileName: String): ByteArray? {
        val file = fileHelper.preparePrivateFile(this, fileName)
        val content = fileHelper.readPrivateFile(file)
        return content
    }
}