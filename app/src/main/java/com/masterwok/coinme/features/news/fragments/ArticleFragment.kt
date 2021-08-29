package com.masterwok.coinme.features.news.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.appbar.AppBarLayout
import com.masterwok.coinme.R
import com.masterwok.coinme.common.activity.WebViewActivity
import com.masterwok.coinme.common.extensions.*
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.databinding.FragmentArticleBinding
import com.masterwok.coinme.di.AppInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.annotation.NonNull

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout.Behavior.DragCallback


class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null

    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val article by lazy {
        checkNotNull(requireArguments().getParcelable<Article>(ARG_ARTICLE)) {
            "Article must be provide through bundle arguments."
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        AppInjector.newsComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation()
        subscribeToViewComponents()
        configure(article)

        binding.appBarLayout.disableDrag()
    }

    @OptIn(FlowPreview::class)
    private fun subscribeToViewComponents() = with(binding) {
        val articleUri = article.articleUri

        buttonContinueReading
            .onClicked()
            .debounce(DEBOUNCE_TIME_MS)
            .onEach { navigateToArticleWebView(articleUri) }
            .launchIn(lifecycleScope)

        floatingActionButtonShare
            .onClicked()
            .debounce(DEBOUNCE_TIME_MS)
            .onEach { requireContext().shareUrl(articleUri.toString()) }
            .launchIn(lifecycleScope)
    }

    private fun navigateToArticleWebView(articleUri: Uri) {
        navController.navigate(
            R.id.action_articleFragment_to_articleWebViewFragment,
            WebViewActivity.createBundle(articleUri)
        )
    }

    private fun initNavigation() = NavigationUI.setupWithNavController(
        binding.toolbar,
        navController
    )

    private fun configure(article: Article) = with(binding) {
        val currentLocale = requireContext().currentLocale

        with(article) {
            imageView.loadImage(articleImageUri)

            textViewTitle.text = title
            textViewContent.text = content
            textViewSource.text = source.name
            textViewPublishedOn.text = publishedOn.getShortDisplayString(currentLocale)
        }
    }

    companion object {
        private const val ARG_ARTICLE = "arg.article"
        private const val DEBOUNCE_TIME_MS = 250L

        @JvmStatic
        fun newBundle(article: Article) = bundleOf(ARG_ARTICLE to article)
    }

}