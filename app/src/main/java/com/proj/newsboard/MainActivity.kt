package com.proj.newsboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.proj.newsboard.newsFragment.NewsFragment

class MainActivity: AppCompatActivity(), FragmentManager {
    override val fragmentStack: MutableList<Fragment> = mutableListOf()
    override val appCompatActivity = this
    override val mainFrame: Int = R.id.mainFrame

    override fun <T: Any> changeUI(targetClass: Class<T>, push: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentStackPush(NewsFragment())
    }
}