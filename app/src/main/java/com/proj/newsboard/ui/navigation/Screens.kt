package com.proj.newsboard.ui.navigation

import androidx.fragment.app.Fragment
import com.proj.newsboard.ui.newsFragment.NewsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object News : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return NewsFragment()
        }
    }
}