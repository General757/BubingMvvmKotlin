package com.bubing.kotlin.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.bindViewPager2
import com.bubing.kotlin.app.ext.init
import com.bubing.kotlin.app.ext.setUiTheme
import com.bubing.kotlin.databinding.FragmentViewpagerBinding
import com.bubing.kotlin.util.CacheUtil
import com.bubing.kotlin.viewmodel.state.TreeViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import kotlinx.android.synthetic.main.include_viewpager.*

/**
 * @ClassName: TreeArrFragment
 * @Author: Bubing
 * @Date: 2020/8/6 5:09 PM
 * @Description: 广场模块父Fragment管理四个子fragment
 */
class TreeArrFragment : BaseFragment<TreeViewModel, FragmentViewpagerBinding>() {

    var titleData = arrayListOf("广场", "每日一问", "体系", "导航")

    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(PlazaFragment())
        fragments.add(AskFragment())
        fragments.add(SystemFragment())
        fragments.add(NavigationFragment())
    }

    override fun layoutId() = R.layout.fragment_viewpager

    override fun initView(savedInstanceState: Bundle?)  {
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { setUiTheme(it, viewpager_linear) }
        include_viewpager_toolbar.run {
            inflateMenu(R.menu.menu_todo)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.todo_add -> {
                        if(CacheUtil.isLogin()){
                            nav().navigateAction(R.id.action_mainfragment_to_addAriticleFragment)
                        }else{
                            nav().navigateAction(R.id.action_to_loginFragment)
                        }
                    }
                }
                true
            }
        }
    }

    override fun lazyLoadData() {
        //初始化viewpager2
        view_pager.init(this, fragments).offscreenPageLimit = fragments.size
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mStringList = titleData) {
            if (it != 0) {
                include_viewpager_toolbar.menu.clear()
            } else {
                include_viewpager_toolbar.menu.hasVisibleItems().let { flag ->
                    if (!flag) include_viewpager_toolbar.inflateMenu(R.menu.menu_todo)
                }
            }
        }
    }

    override fun createObserver() {
        appViewModel.appColor.observe(viewLifecycleOwner, Observer {
            setUiTheme(it, viewpager_linear)
        })
    }

}