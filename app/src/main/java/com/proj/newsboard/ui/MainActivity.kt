package com.proj.newsboard.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.proj.newsboard.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity: AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navHolder: NavigatorHolder

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private val navigator = SupportAppNavigator(
        this,
        supportFragmentManager,
        R.id.mainFrame
    )

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    override fun onResume() {
        super.onResume()
        navHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.init()
        setNavListener()
    }

    private fun setNavListener() {
        navView.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_news -> {
                    viewModel.onNewsClick()
                }
                R.id.nav_settings -> {}
            }
            mainDrawerLayout.closeDrawers()
            true
        }
    }
}