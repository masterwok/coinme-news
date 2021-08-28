package com.masterwok.coinme.features.news.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.databinding.FragmentArticleBinding
import com.masterwok.coinme.di.AppInjector

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null

    private val binding get() = _binding!!

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

        configure(article)
    }

    private fun configure(article: Article) = with(binding) {
        textViewContent.text = article.content
    }

    companion object {
        private const val ARG_ARTICLE = "arg.article"

        @JvmStatic
        fun newBundle(article: Article) = bundleOf(ARG_ARTICLE to article)
    }

}