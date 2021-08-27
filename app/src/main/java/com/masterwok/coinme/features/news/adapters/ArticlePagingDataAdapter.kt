package com.masterwok.coinme.features.news.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.masterwok.coinme.R
import com.masterwok.coinme.common.contracts.Configurable
import com.masterwok.coinme.data.repositories.models.Article
import com.masterwok.coinme.databinding.ViewHolderArticleBinding

class ArticlePagingDataAdapter : PagingDataAdapter<Article, ArticlePagingDataAdapter.ViewHolder>(
    DIFF_CALLBACK
) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_article, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let(holder::configure)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), Configurable<Article> {

        private val binding = ViewHolderArticleBinding.bind(view)

        override fun configure(model: Article) {
            with(binding) {
                textViewTitle.text = model.title
                textViewDescription.text = model.description

                Glide
                    .with(imageView)
                    .load(model.articleImageUri)
                    .placeholder(CircularProgressDrawable(itemView.context).apply {
                        strokeWidth = PLACEHOLDER_IMAGE_STROKE_WIDTH
                        centerRadius = PLACEHOLDER_IMAGE_CENTER_RADIUS
                        start()
                    })
                    .centerCrop()
                    .into(imageView)
            }
        }
    }

    private companion object {
        private const val PLACEHOLDER_IMAGE_STROKE_WIDTH = 5f
        private const val PLACEHOLDER_IMAGE_CENTER_RADIUS = 30f

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