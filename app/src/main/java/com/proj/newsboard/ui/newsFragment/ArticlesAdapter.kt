package com.proj.newsboard.ui.newsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.proj.newsboard.databinding.LayoutNewsBinding
import com.proj.newsboard.model.Article

class ArticlesAdapter:
    PagedListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(diffUtilCallback) {
    companion object {
        private val diffUtilCallback = object: DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.description == newItem.description &&
                        oldItem.publishedAt == newItem.publishedAt &&
                        oldItem.source == newItem.source &&
                        oldItem.title == newItem.title &&
                        oldItem.url == newItem.url &&
                        oldItem.urlToImage == newItem.urlToImage
            }

        }
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutNewsBinding = LayoutNewsBinding.inflate(inflater, parent, false)
        return ArticlesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = getItem(position)

        holder.binding?.article = article
        holder.binding?.descriptionLayout?.visibility = View.GONE
        holder.binding?.clickHandler = BindingAdapter.ClickHandler()
    }

    inner class ArticlesViewHolder internal constructor(view: View):
        RecyclerView.ViewHolder(view) {
        val binding: LayoutNewsBinding? = androidx.databinding.DataBindingUtil.bind(view)
    }
}