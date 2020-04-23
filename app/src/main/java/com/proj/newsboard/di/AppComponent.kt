package com.proj.newsboard.di

import com.proj.newsboard.app.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: App)
}