/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
* limitations under the License.
 */
package com.example.exoplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.exoplayer.databinding.ActivityPlayerBinding

private const val TAG = "PlayerActivity"

private class MyWebViewClient : WebViewClient() {
    fun openLink(view: WebView, url: String) {
        var urlWithHttp = url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            urlWithHttp = "http://$url"
        }
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlWithHttp))
        try {
            view.context.startActivity(browserIntent)
        } catch (e: Exception) {
            // Handle the case where no suitable activity is found
            println("No suitable activity found to handle the URL")
            // Potentially display an error message to the user
        }
    }
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val regex = "hymnal.net".toRegex()
        if (regex.containsMatchIn(url)) {
            openLink(view, url)
        } else {
            view.loadUrl(url)
        }
        return true
    }

    override fun onLoadResource(view: WebView?, url: String) {
        val regex = "hymnal.net".toRegex()
        if (regex.containsMatchIn(url)) {
            if (view != null) {
                openLink(view, url)
            }
        } else {
            super.onLoadResource(view, url)
        }
    }
}

/**
 * A fullscreen activity to play audio or video streams.
 */
class PlayerActivity : AppCompatActivity() {
    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPlayerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val myWebView: WebView = findViewById(R.id.web_view)
        myWebView.webViewClient = MyWebViewClient()
        myWebView.settings.javaScriptEnabled = true
        //myWebView.settings.domStorageEnabled = true
        myWebView.loadUrl("https://webapp.hymnsradio.com/")
    }
}