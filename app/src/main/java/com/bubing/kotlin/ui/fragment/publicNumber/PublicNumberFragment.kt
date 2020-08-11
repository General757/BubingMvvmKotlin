package com.bubing.kotlin.ui.fragment.publicNumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.include_viewpager.*
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.*
import com.bubing.kotlin.weight.callback.ErrorCallback
import com.bubing.kotlin.data.model.bean.ClassifyResponse
import com.bubing.kotlin.databinding.FragmentViewpagerBinding
import com.bubing.kotlin.viewmodel.request.RequestPublicNumberViewModel
import com.bubing.mvvmk.ext.parseState

/**
 * @ClassName: PublicNumberFragment
 * @Author: Bubing
 * @Date: 2020/8/6 5:09 PM
 * @Description: 聚合公众号
 */
class PublicNumberFragment :
    BaseFragment<RequestPublicNumberViewModel, FragmentViewpagerBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //fragment集合
    private var fragments: ArrayList<Fragment> = arrayListOf()

    //标题集合
    private var mDataList: ArrayList<ClassifyResponse> = arrayListOf()

    override fun layoutId() = R.layout.fragment_viewpager

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(view_pager) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModel.getPublicTitleData()
        }
        //初始化viewpager2
        view_pager.init(this, fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mDataList)
        appViewModel.appColor.value?.let { setUiTheme(it, viewpager_linear, loadsir) }
    }

    /**
     * 懒加载
     */
    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        //请求标题数据
        mViewModel.getPublicTitleData()
    }

    override fun createObserver() {
        mViewModel.titleData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                mDataList.addAll(it)
                it.forEach { classify ->
                    fragments.add(PublicChildFragment.newInstance(classify.id))
                }
                magic_indicator.navigator.notifyDataSetChanged()
                view_pager.adapter?.notifyDataSetChanged()
                view_pager.offscreenPageLimit = fragments.size
                loadsir.showSuccess()
            }, {
                //请求项目标题失败
                loadsir.showCallback(ErrorCallback::class.java)
                loadsir.setErrorText(it.errorMsg)
            })
        })
        appViewModel.appColor.observe(viewLifecycleOwner, Observer {
            setUiTheme(it, viewpager_linear, loadsir)
        })
    }
}