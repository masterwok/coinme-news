package com.masterwok.coinme.common.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masterwok.coinme.R
import com.masterwok.coinme.common.contracts.Configurable
import com.masterwok.coinme.databinding.ViewHolderSpinnerLoadStateBinding

/**
 * A [LoadStateAdapter] that presents a spinner and is intended to be used as a footer adapter.
 */
internal class SpinnerLoadStateAdapter(
    private val onRetryClickListener: () -> Unit
) : LoadStateAdapter<SpinnerLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_spinner_load_state, parent, false),
        onRetryClickListener
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState
    ) = holder.configure(loadState)

    class ViewHolder(
        view: View,
        private val onRetryClickListener: () -> Unit
    ) : RecyclerView.ViewHolder(view), Configurable<LoadState> {

        private val binding = ViewHolderSpinnerLoadStateBinding.bind(view)

        override fun configure(model: LoadState) {
            when (model) {
                is LoadState.Error -> with(binding) {
                    loadStateProgress.isVisible = false
                    buttonRetry.isVisible = true
                    buttonRetry.setOnClickListener {
                        buttonRetry.isVisible = false
                        onRetryClickListener()
                    }
                }
                else -> with(binding) {
                    loadStateProgress.isVisible = true
                    buttonRetry.isVisible = false
                }
            }
        }
    }
}
