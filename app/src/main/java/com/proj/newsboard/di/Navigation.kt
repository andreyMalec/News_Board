package com.proj.memeboard.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class Navigation {
    @Provides
    @Singleton
    fun cicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun router(cicerone: Cicerone<Router>) = cicerone.router

    @Provides
    @Singleton
    fun navHolder(cicerone: Cicerone<Router>) = cicerone.navigatorHolder
}