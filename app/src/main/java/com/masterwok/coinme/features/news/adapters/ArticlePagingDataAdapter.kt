package com.masterwok.coinme.features.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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

        override fun configure(model: Article) = with(binding) {
            textViewTitle.text = model.title
        }
    }

    private companion object {
        private val DIFF_CALLBACK by lazy {
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean = oldItem.url == newItem.url

                override fun areContentsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean = oldItem == newItem

            }
        }
    }

}

//class UserAdapter(diffCallback: DiffUtil.ItemCallback<User>) :
//    PagingDataAdapter<User, UserViewHolder>(diffCallback) {
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): UserViewHolder {
//        return UserViewHolder(parent)
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val item = getItem(position)
//        // Note that item may be null. ViewHolder must support binding a
//        // null item as a placeholder.
//        holder.bind(item)
//    }
//}
