package com.bubing.mvvmk.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bubing.mvvmk.base.viewmodel.BaseViewModel

/**
 * @ClassName: BaseVmVdbFragment
 * @Author: Bubing
 * @Date: 2020/8/6 1:53 PM
 * @Description: ViewModelFragment基类，自动把ViewModel注入Fragment和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseVmVdbFragment<VM : BaseViewModel, VDB : ViewDataBinding> : BaseVmFragment<VM>() {

    //该类绑定的ViewDataBinding
    lateinit var mDatabind: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabind = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mDatabind.lifecycleOwner = this
        return mDatabind.root
    }
}