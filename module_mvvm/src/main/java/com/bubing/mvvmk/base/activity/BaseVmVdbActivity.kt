package com.bubing.mvvmk.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bubing.mvvmk.base.viewmodel.BaseViewModel

/**
 * @ClassName: BaseActivity
 * @Author: Bubing
 * @Date: 2020/8/5 5:17 PM
 * @Description: 包含ViewModel 和Databind ViewModelActivity基类，把ViewModel 和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseVmVdbActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseVmActivity<VM>() {

    lateinit var mViewDataBind: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    override fun initDataBind() {
        mViewDataBind = DataBindingUtil.setContentView(this, layoutId())
        mViewDataBind.lifecycleOwner = this
    }
}