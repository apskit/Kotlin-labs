package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ksg.mso.filehelp.FilesHelper

class MainActivity : AppCompatActivity() {
    private val fileHelper = FilesHelper()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // New file button
        val openFileButton = findViewById<Button>(R.id.buttonOpenFile)
        openFileButton.setOnClickListener {
            createNewFile()
        }

        // Load file list
        val listView = findViewById<ListView>(R.id.filesList)
        loadFileList()

        listView.setOnItemClickListener { _, _, position, _ ->
            val fileName = adapter.getItem(position)
            if (fileName != null) {
                openEditor(fileName)
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        loadFileList()

    }

    private fun loadFileList() {
        val privateAppDirPath = applicationContext.filesDir
        val listView = findViewById<ListView>(R.id.filesList)
        val files = privateAppDirPath.list { _, name -> name.endsWith(".txt") } ?: arrayOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, files.toList())
        listView.adapter = adapter
    }

    private fun openEditor(fileName: String) {
        val intent = Intent(this, EditorActivity::class.java)
        intent.putExtra("FILE_NAME", fileName)
        startActivity(intent)
    }

    private fun createNewFile() {
        val fileName = "new_file_${System.currentTimeMillis()}.txt"
        val file = fileHelper.preparePrivateFile(this, fileName)
        fileHelper.writeToPrivateFile(file, "".toByteArray())

        loadFileList()
        openEditor(fileName)
    }
}