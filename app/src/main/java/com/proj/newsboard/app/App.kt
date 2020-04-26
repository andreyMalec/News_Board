package com.proj.newsboard.app

import android.app.Activity
import android.app.Application
import com.proj.newsboard.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class App: Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        AppInjector.init(this)

        super.onCreate()
    }

    override fun activityInjector() = dispatchingAndroidInjector
}