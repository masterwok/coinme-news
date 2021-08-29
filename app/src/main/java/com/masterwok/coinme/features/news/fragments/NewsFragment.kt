package com.masterwok.coinme.features.news.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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
import com.masterwok.coinme.common.extensions.searchManager
import com.masterwok.coinme.common.utils.presentNetworkFailureDialog
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.databinding.FragmentNewsBinding
import com.masterwok.coinme.di.AppInjector
import com.masterwok.coinme.features.news.NewsViewModel
import com.masterwok.coinme.features.news.adapters.ArticlePagingDataAdapter
import kotlinx.coroutines.flow.collect
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

    private val currentFilter get() = viewModel.filterStateFlow.value

    private val searchView
        get() = binding
            .toolbar
            .menu
            .findItem(R.id.menu_item_search)
            .actionView as SearchView

    private val searchViewQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.searchNews(currentFilter.copy(query = query))
            searchView.clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        AppInjector.newsComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavigation()
        initSearchView()
        initRecyclerView()
        observeViewModel()

        subscribeToViewComponents()
    }

    private fun initNavigation() {
        val toolbar = binding.toolbar.apply {
            inflateMenu(R.menu.news_fragment)
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.newsFragment)
        )

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
    }

    private fun initSearchView() = with(requireActivity()) {
        searchManager
            .getSearchableInfo(componentName)
            .let(searchView::setSearchableInfo)
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

    private fun subscribeToViewComponents() = with(binding) {
        swipeRefreshLayout.setOnRefreshListener { articleAdapter.refresh() }

        searchView.setOnQueryTextListener(searchViewQueryTextListener)
        searchView.setOnSearchClickListener {
            searchView.post {
                searchView.setQuery(currentFilter.query, false)
            }
        }
    }

    private fun observeViewModel() = with(viewLifecycleOwner.lifecycleScope) {
        launch {
            viewModel.articlePagingDataFlow.collectLatest { pagingData ->
                articleAdapter.submitData(pagingData)
            }
        }

        launch {
            viewModel.filterStateFlow.collect { filter ->
                searchView.setQuery(filter.query, false)
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