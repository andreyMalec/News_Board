package com.proj.newsboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.proj.newsboard.FragmentManager
import com.proj.newsboard.R
import com.proj.newsboard.ui.newsFragment.NewsFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity: AppCompatActivity(), FragmentManager, HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override val fragmentStack: MutableList<Fragment> = mutableListOf()
    override val appCompatActivity = this
    override val mainFrame: Int = R.id.mainFrame

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun <T: Any> changeUI(targetClass: Class<T>, push: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentStackPush(NewsFragment())
    }
}