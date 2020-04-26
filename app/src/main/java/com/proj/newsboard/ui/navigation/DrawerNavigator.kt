package com.proj.newsboard.ui.navigation

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Replace

class DrawerNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    containerId: Int
): SupportAppNavigator(activity, fragmentManager, containerId) {

    private val screens = mutableListOf<String>()
    private lateinit var currentScreen: String

    override fun fragmentReplace(command: Replace) {
        val screen = command.screen as SupportAppScreen
        val screenKey = screen.screenKey

        if (::currentScreen.isInitialized && screenKey == currentScreen) return
        else currentScreen = screenKey

        fragmentManager.beginTransaction().apply {
            hideAll()
            show(screen)
            commit()
        }
    }

    private fun FragmentTransaction.show(screen: SupportAppScreen) {
        val fragment = createFragment(screen) ?: return

        if (!screens.contains(screen.screenKey)) {
            screens.add(screen.screenKey)
            add(containerId, fragment, screen.screenKey)
        }

        fragmentManager.findFragmentByTag(screen.screenKey)?.let {
            show(it)
        }
    }

    private fun FragmentTransaction.hideAll() {
        screens.forEach { key ->
            fragmentManager.findFragmentByTag(key)?.let {
                hide(it)
            }
        }
    }
}