package com.bubing.kotlin.ui.fragment.demo

import android.os.Bundle
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.initClose
import com.bubing.kotlin.databinding.FragmentDemoBinding
import com.bubing.kotlin.viewmodel.state.DemoViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @ClassName: DemoFragment
 * @Author: Bubing
 * @Date: 2020/8/11 2:55 PM
 * @Description: 放一些示例，目前只有 文件下载示例 后面想到什么加什么，作者那个比很懒，佛性添加
 */
class DemoFragment : BaseFragment<DemoViewModel, FragmentDemoBinding>() {

    override fun layoutId() = R.layout.fragment_demo

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()

        toolbar.initClose("示例") {
            nav().navigateUp()
        }
    }


    inner class ProxyClick {
        fun download() {
            //测试一下 普通的下载
            nav().navigateAction(R.id.action_demoFragment_to_downLoadFragment)
        }

        fun downloadLibrary() {
            //测试一下利用三方库下载
            nav().navigateAction(R.id.action_demoFragment_to_downLoadLibraryFragment)
        }
    }


}