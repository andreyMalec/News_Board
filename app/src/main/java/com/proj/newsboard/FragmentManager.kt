package com.proj.newsboard

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface FragmentManager {
    val fragmentStack: MutableList<Fragment>
    val appCompatActivity: AppCompatActivity
    val mainFrame: Int

    fun <T: Any> changeUI(targetClass: Class<T>, push: Boolean)

    fun fragmentStackPush(newFragment: Fragment) {
        val fragmentTransaction = appCompatActivity.supportFragmentManager.beginTransaction()
        if (fragmentStack.size > 0)
            fragmentTransaction.hide(fragmentStack.last())

        fragmentTransaction.add(mainFrame, newFragment)
        fragmentStack.add(newFragment)
        fragmentTransaction.commit()

        changeUI(newFragment.javaClass, true)
    }

    fun fragmentStackPop(): Boolean {
        if (fragmentStack.size > 1) {
            val ft = appCompatActivity.supportFragmentManager.beginTransaction()
            val leaveFragment = fragmentStack.last()
            ft.remove(leaveFragment)
            fragmentStack.remove(leaveFragment)
            ft.show(fragmentStack.last())
            ft.commit()

            changeUI(leaveFragment.javaClass, false)

            return true
        }
        return false
    }
}