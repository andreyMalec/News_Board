package com.proj.newsboard.di.activity

import com.proj.newsboard.ui.newsFragment.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@ExperimentalCoroutinesApi
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment
}