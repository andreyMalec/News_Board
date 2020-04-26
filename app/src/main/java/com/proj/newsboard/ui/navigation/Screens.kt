package com.proj.newsboard.ui.navigation

import androidx.fragment.app.Fragment
import com.proj.newsboard.ui.news.NewsFragment
import com.proj.newsboard.ui.settings.SettingsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object News : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return NewsFragment()
        }
    }

    object Settings : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return SettingsFragment()
        }
    }
}