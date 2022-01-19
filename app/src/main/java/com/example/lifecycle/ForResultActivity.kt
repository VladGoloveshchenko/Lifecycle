package com.example.lifecycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ForResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_result)

        findViewById<EditText>(R.id.edit_text).setOnEditorActionListener { view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                setResult(Activity.RESULT_OK, Intent().putExtra(ARG_RESULT, view.text.toString()))
                finish()
                true
            } else false
        }
    }

    companion object {
        const val ARG_RESULT = "arg_result"
    }
}