package com.bubing.kotlin.ui.fragment.collect
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_collect.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.bubing.kotlin.R
import com.bubing.kotlin.viewmodel.request.RequestCollectViewModel
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.bindViewPager2
import com.bubing.kotlin.app.ext.init
import com.bubing.kotlin.app.ext.initClose
import com.bubing.kotlin.databinding.FragmentCollectBinding
import com.bubing.mvvmk.ext.nav
/**
 * @ClassName: CollectFragment
 * @Author: Bubing
 * @Date: 2020/8/11 2:48 PM
 * @Description: 收藏
 */
class CollectFragment:BaseFragment<RequestCollectViewModel,FragmentCollectBinding>() {

    var titleData = arrayListOf("文章","网址")

    private var fragments : ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(CollectAriticleFragment())
        fragments.add(CollectUrlFragment())
    }
    override fun layoutId() = R.layout.fragment_collect

    override fun initView(savedInstanceState: Bundle?)  {
        //初始化时设置顶部主题颜色
        appViewModel.appColor.value?.let { collect_viewpager_linear.setBackgroundColor(it) }
        //初始化viewpager2
        collect_view_pager.init(this,fragments)
        //初始化 magic_indicator
        collect_magic_indicator.bindViewPager2(collect_view_pager,mStringList = titleData)
        toolbar.initClose(){
            nav().navigateUp()
        }

    }
}