package com.masterwok.coinme.features.news.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.masterwok.coinme.databinding.FragmentArticleWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: FragmentArticleWebViewBinding

    private val articleUri by lazy {
        checkNotNull(intent.getParcelableExtra<Uri>(EXTRA_ARTICLE_URI)) {
            "Article Uri must be provide through bundle arguments."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentArticleWebViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initWebView()

        configure(articleUri)
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