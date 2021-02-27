package com.alkhademy.newsapp.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alkhademy.newsapp.R
import com.alkhademy.newsapp.databinding.ActivityDetailNewsBinding
import com.alkhademy.newsapp.databinding.ContentDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val content: ContentDetailNewsBinding = binding.contentNews

        if (isConnect()) {
            content.apply {
                val url = intent.getStringExtra("url")
                webView.webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        view.loadUrl(url)
                        return false
                    }

                    override fun onPageFinished(view: WebView, url: String) {
                        super.onPageFinished(view, url)
                        progressBar.visibility = View.GONE
                    }
                }

                webView.settings.javaScriptEnabled = true
                webView.settings.setSupportZoom(true)
                if (url != null) {
                    webView.loadUrl(url)
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.not_connect), Toast.LENGTH_LONG).show()
            content.progressBar.visibility = View.GONE

        }
    }

    private fun isConnect(): Boolean {
        val connect: ConnectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}