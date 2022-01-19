package com.example.lifecycle

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val webView = findViewById<WebView>(R.id.web_view)

        webView.apply {
            loadUrl(intent.data?.toString() ?: "")

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    loadUrl(url ?: "")
                    return true
                }
            }
        }
    }
}