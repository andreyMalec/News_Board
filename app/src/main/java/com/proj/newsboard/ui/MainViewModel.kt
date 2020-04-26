package com.proj.newsboard.ui

import androidx.lifecycle.ViewModel
import com.proj.newsboard.ui.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(private val router: Router) : ViewModel() {
    fun init() {
        onNewsClick()
    }

    fun onNewsClick() {
        router.replaceScreen(Screens.News)
    }
}