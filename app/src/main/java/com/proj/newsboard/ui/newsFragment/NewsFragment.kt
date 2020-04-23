package com.proj.newsboard.ui.newsFragment

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proj.newsboard.R
import com.proj.newsboard.di.Injectable
import com.proj.newsboard.model.Category
import com.proj.newsboard.util.DateRangePicker
import kotlinx.android.synthetic.main.news_fragment.*
import javax.inject.Inject

class NewsFragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: NewsViewModel by viewModels {
        viewModelFactory
    }

    private val adapter = ArticlesAdapter()
    private var datePicker: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_toolbar_menu, menu)

        val searchMenuItem = menu.findItem(R.id.news_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.news_search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMenuItem.collapseActionView()

                if (!query.isNullOrBlank())
                    viewModel.searchNews(query)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        datePicker = menu.findItem(R.id.date_picker)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.date_picker -> {
                DateRangePicker(requireActivity(), viewModel::pickDateRange).pick()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.datePickerVisibility.observe(viewLifecycleOwner, Observer {
            datePicker?.isVisible = viewModel.datePickerVisibility.value!!

            if (viewModel.datePickerVisibility.value == true) categoryLayout.clearCheck()
        })

        newsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsRecycler.adapter = adapter
        newsRecycler.scrollToPosition(0)

        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) newsRecycler.scrollToPosition(0)
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
            }
        }
    }
}