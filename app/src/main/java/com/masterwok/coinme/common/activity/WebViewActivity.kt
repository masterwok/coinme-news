package com.masterwok.coinme.common.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.masterwok.coinme.R
import com.masterwok.coinme.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    private val articleUri by lazy {
        checkNotNull(intent.getParcelableExtra<Uri>(EXTRA_ARTICLE_URI)) {
            "Article Uri must be provide through bundle arguments."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initToolbar()
        initWebView()

        configure(articleUri)
    }

    private fun initToolbar() = with(binding.toolbar) {
        setNavigationIcon(R.drawable.ic_baseline_close_24)
        setNavigationOnClickListener { finish() }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() = with(binding.webView) {
        settings.apply {
            javaScriptEnabled = true
            databaseEnabled = true
            domStorageEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            displayZoomControls = false

            setSupportZoom(true)
            setSupportMultipleWindows(true)
        }

        webChromeClient = WebChromeClient()
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.url?.toString()?.let { view?.loadUrl(it) }
                return true
            }
        }
    }

    override fun onBackPressed() = with(binding.webView) {
        if (canGoBack()) {
            goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun configure(articleUri: Uri) = binding.webView.loadUrl(articleUri.toString())

    companion object {
        private const val EXTRA_ARTICLE_URI = "intent.article_uri"

        @JvmStatic
        fun createBundle(
            articleUri: Uri
        ) = bundleOf(EXTRA_ARTICLE_URI to articleUri)
    }

}