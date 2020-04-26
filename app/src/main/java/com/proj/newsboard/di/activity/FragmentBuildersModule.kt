package com.proj.newsboard.di.activity

import com.proj.newsboard.ui.news.NewsFragment
import com.proj.newsboard.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@ExperimentalCoroutinesApi
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment
}