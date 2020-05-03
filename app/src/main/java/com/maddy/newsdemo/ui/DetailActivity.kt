package com.maddy.newsdemo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.maddy.newsdemo.R
import com.maddy.newsdemo.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_detail

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideProgress()
        showData()
        setToolbarText("News Details")
        val url: String = intent.getStringExtra("url");
        binding.webDetail.settings.javaScriptEnabled = true;
        binding.webDetail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        binding.webDetail.loadUrl(url)

    }

}