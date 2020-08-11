package com.bubing.kotlin.ui.fragment.tree
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.bindViewPager2
import com.bubing.kotlin.app.ext.init
import com.bubing.kotlin.app.ext.initClose
import com.bubing.kotlin.data.model.bean.SystemResponse
import com.bubing.kotlin.databinding.FragmentSystemBinding
import com.bubing.kotlin.viewmodel.state.TreeViewModel
import com.bubing.mvvmk.ext.nav
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.include_viewpager.*

/**
 * @ClassName: SystemArrFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:12 PM
 * @Description: java类作用描述
 */
class SystemArrFragment : BaseFragment<TreeViewModel, FragmentSystemBinding>() {

    lateinit var data: SystemResponse

    var index = 0

    private var fragments: ArrayList<Fragment> = arrayListOf()

    override fun layoutId() = R.layout.fragment_system

    override fun initView(savedInstanceState: Bundle?)  {
        arguments?.let {
            data = it.getParcelable("data")!!
            index = it.getInt("index")
        }
        toolbar.initClose(data.name) {
            nav().navigateUp()
        }
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { viewpager_linear.setBackgroundColor(it) }
        //设置栏目标题居左显示
        (magic_indicator.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.LEFT

    }

    override fun lazyLoadData() {
        data.children.forEach {
            fragments.add(SystemChildFragment.newInstance(it.id))
        }
        //初始化viewpager2
        view_pager.init(this, fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, data.children)

        view_pager.offscreenPageLimit = fragments.size

        view_pager.postDelayed({
            view_pager.currentItem = index
        },100)

    }

    override fun createObserver() {

    }

}