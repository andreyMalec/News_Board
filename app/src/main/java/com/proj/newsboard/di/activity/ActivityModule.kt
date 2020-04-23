package com.proj.newsboard.di.activity

import com.proj.newsboard.di.module.DbModule
import com.proj.newsboard.di.module.NetworkModule
import com.proj.newsboard.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module(includes = [NetworkModule::class, DbModule::class])
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}