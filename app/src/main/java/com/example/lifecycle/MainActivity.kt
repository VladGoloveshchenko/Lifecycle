package com.example.lifecycle

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lifecycle.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainLifecycleActivity ${Random.nextInt(0, 100)}"

    private lateinit var binding: ActivityMainBinding

    private val activityForResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val input = result.data?.getStringExtra(ForResultActivity.ARG_RESULT) ?: ""
            binding.textForResult.text = input
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            buttonDate.setOnClickListener {
                val intent = Intent(this@MainActivity, DateActivity::class.java)
                startActivity(intent)

//                val intent = Intent("com.example.lifecycle.intent.date")
//                startActivity(intent)
            }

            buttonTime.setOnClickListener {
                val intent = Intent("com.example.lifecycle.intent.time")
                startActivity(intent)

//                startActivity(Intent(this@MainActivity, AlertActivity::class.java))
            }

            buttonContainer.setOnClickListener {
                startActivity(Intent(this@MainActivity, ContainerActivity::class.java))
            }

            editText.setOnEditorActionListener { view, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val intent = Intent(this@MainActivity, TextActivity::class.java)
                        .putExtra(TextActivity.ARG_TEXT, editText.text.toString())
                    startActivity(intent)
                    true
                } else {
                    false
                }
            }

            buttonUri.setOnClickListener {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"))
                val geoIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo: 53.7169415, 27.9775789"))
                val telIntent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:12345"))
                startActivity(webIntent)
            }

            textForResult.setOnClickListener {
                activityForResultLauncher.launch(
                    Intent(this@MainActivity, ForResultActivity::class.java)
                )
            }
        }

        Log.d(TAG, "onCreate bundle = $savedInstanceState")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}