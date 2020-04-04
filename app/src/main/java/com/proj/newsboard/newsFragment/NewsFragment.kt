package com.proj.newsboard.newsFragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proj.newsboard.R
import com.proj.newsboard.dataClass.Category
import kotlinx.android.synthetic.main.news_fragment.*

class NewsFragment: Fragment() {
    private lateinit var viewModel: NewsViewModel
    private val adapter = ArticlesAdapter()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.articles.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        newsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsRecycler.adapter = adapter
        newsRecycler.scrollToPosition(0)

        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    newsRecycler.smoothScrollToPosition(0)
                }
            }
        })

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateNews()
            swipeRefreshLayout.isRefreshing = false
        }

        for (category in categoryLayout.children) {
            category.setOnClickListener {
                viewModel.loadCategory(Category.from(it.tag.toString()))
                it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.category_click_animation))

                (it.parent as View).isEnabled = false
                Handler().postDelayed({ (it.parent as View).isEnabled = true }, 500)

                newsRecycler.scrollToPosition(0)
            }
        }
    }
}