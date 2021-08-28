package com.masterwok.coinme.features.news.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.masterwok.coinme.R
import com.masterwok.coinme.common.adapters.SpinnerLoadStateAdapter
import com.masterwok.coinme.common.utils.presentNetworkFailureDialog
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.databinding.FragmentNewsBinding
import com.masterwok.coinme.di.AppInjector
import com.masterwok.coinme.features.news.NewsViewModel
import com.masterwok.coinme.features.news.adapters.ArticlePagingDataAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: NewsViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val articleAdapter = ArticlePagingDataAdapter(this::navigateToArticleDetail)

    private val spinnerLoadStateAdapter = SpinnerLoadStateAdapter { articleAdapter.retry() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        AppInjector.newsComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation()
        initRecyclerView()
        observeViewModel()

        subscribeToViewComponents()
    }

    private fun subscribeToViewComponents() = with(binding) {
        swipeRefreshLayout.setOnRefreshListener { articleAdapter.refresh() }
    }

    private fun initNavigation() {
        val toolbar = binding.toolbar

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.newsFragment)
        )

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        adapter = ConcatAdapter(
            articleAdapter.apply {
                addLoadStateListener(::onLoadStateListenerChange)
                withLoadStateFooter(spinnerLoadStateAdapter)
            },
            spinnerLoadStateAdapter
        )

        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun observeViewModel() = with(viewLifecycleOwner.lifecycleScope) {
        launch {
            viewModel.articlePagingDataFlow.collectLatest {
                articleAdapter.submitData(it)
            }
        }
    }

    private fun onLoadStateListenerChange(listener: CombinedLoadStates) {
        with(binding) {
            when (val refresh = listener.refresh) {
                LoadState.Loading -> swipeRefreshLayout.isRefreshing = true
                is LoadState.NotLoading -> swipeRefreshLayout.isRefreshing = false
                is LoadState.Error -> {
                    swipeRefreshLayout.isRefreshing = false

                    presentNetworkFailureDialog(
                        requireContext(),
                        refresh.error,
                        articleAdapter::refresh
                    )
                }
            }
        }
    }


    private fun navigateToArticleDetail(article: Article) = navController.navigate(
        R.id.action_newsFragment_to_articleFragment,
        ArticleFragment.newBundle(article)
    )


}