package com.bubing.kotlin.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.init
import com.bubing.kotlin.app.ext.initMain
import com.bubing.kotlin.app.ext.setUiTheme
import com.bubing.kotlin.databinding.ActivityMainBinding
import com.bubing.kotlin.viewmodel.state.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @ClassName: MainFragment
 * @Author: Bubing
 * @Date: 2020/8/7 1:26 PM
 * @Description: 项目主页Fragment
 */
class MainFragment : BaseFragment<MainViewModel, ActivityMainBinding>() {
    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager
        mainViewpager.initMain(this)
        //初始化bottombar
        mainBottom.init {
            when (it) {
                R.id.menu_main -> mainViewpager.setCurrentItem(0, false)
                R.id.menu_project -> mainViewpager.setCurrentItem(1, false)
                R.id.menu_system -> mainViewpager.setCurrentItem(2, false)
                R.id.menu_public -> mainViewpager.setCurrentItem(3, false)
                R.id.menu_me -> mainViewpager.setCurrentItem(4, false)
            }
        }
    }

    override fun createObserver() {
        appViewModel.appColor.observe(this, Observer { setUiTheme(it, mainBottom) })
    }
}