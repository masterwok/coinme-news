package com.masterwok.coinme.features.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.masterwok.coinme.databinding.FragmentNewsBinding
import com.masterwok.coinme.di.AppInjector
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

    private val articleAdapter = ArticlePagingDataAdapter()

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

        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        adapter = articleAdapter
        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun observeViewModel() = with(viewLifecycleOwner.lifecycleScope) {
        launch {
            viewModel.articlePagingDataFlow.collectLatest {
                articleAdapter.submitData(it)
            }
        }
    }


}