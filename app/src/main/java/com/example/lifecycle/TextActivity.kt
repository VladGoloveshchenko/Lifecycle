package com.example.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        val userAction = intent.action
        val userText = intent.getStringExtra(ARG_TEXT)

        findViewById<TextView>(R.id.text).text = userText
    }

    companion object {
        const val ARG_TEXT = "arg_text"
    }
}