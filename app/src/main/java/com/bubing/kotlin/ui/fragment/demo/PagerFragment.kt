package com.bubing.kotlin.ui.fragment.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.databinding.FragmentPagerBinding
import com.bubing.kotlin.ui.fragment.collect.CollectFragment
import com.bubing.kotlin.ui.fragment.search.SearchFragment
import com.bubing.kotlin.ui.fragment.share.AriticleFragment
import com.bubing.kotlin.ui.fragment.todo.TodoListFragment
import com.bubing.kotlin.viewmodel.state.MainViewModel
import kotlinx.android.synthetic.main.fragment_pager.*

/**
 * @ClassName: PagerFragment
 * @Author: Bubing
 * @Date: 2020/8/11 2:58 PM
 * @Description: 测试 Viewpager下的懒加载
 */
class PagerFragment : BaseFragment<MainViewModel, FragmentPagerBinding>() {

    override fun layoutId() = R.layout.fragment_pager

    override fun initView(savedInstanceState: Bundle?) {
        pagerViewpager.adapter = object : FragmentStatePagerAdapter(childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        SearchFragment()
                    }
                    1 -> {
                        TodoListFragment()
                    }
                    2 -> {
                        AriticleFragment()
                    }
                    else -> {
                        CollectFragment()
                    }
                }
            }

            override fun getCount(): Int {
                return 5;
            }
        }
        pagerViewpager.offscreenPageLimit = 5
    }
}