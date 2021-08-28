package com.masterwok.coinme.features.news.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.masterwok.coinme.R
import com.masterwok.coinme.common.contracts.Configurable
import com.masterwok.coinme.common.extensions.loadImage
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.databinding.ViewHolderArticleBinding

class ArticlePagingDataAdapter(
    private val onArticleTapped: (article: Article) -> Unit
) : PagingDataAdapter<Article, ArticlePagingDataAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        onArticleTapped,
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_article, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let(holder::configure)
    }

    class ViewHolder(
        private val onArticleTapped: (article: Article) -> Unit,
        view: View
    ) : RecyclerView.ViewHolder(view), Configurable<Article> {

        private val binding = ViewHolderArticleBinding.bind(view)

        override fun configure(model: Article) {
            itemView.setOnClickListener { onArticleTapped(model) }

            with(binding) {
                textViewTitle.text = model.title
                textViewDescription.text = model.description
                imageView.loadImage(model.articleImageUri)
            }
        }
    }

    private companion object {
        private val DIFF_CALLBACK by lazy {
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean = oldItem.articleUri == newItem.articleUri

                override fun areContentsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean = oldItem == newItem

            }
        }
    }

}