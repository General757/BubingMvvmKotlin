package com.bubing.kotlin.app.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.bubing.kotlin.app.event.AppViewModel
import com.bubing.kotlin.app.event.EventViewModel
import com.bubing.kotlin.app.ext.dismissLoadingExt
import com.bubing.kotlin.app.ext.hideSoftKeyboard
import com.bubing.kotlin.app.ext.showLoadingExt
import com.bubing.mvvmk.base.fragment.BaseVmVdbFragment
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.getAppViewModel

/**
 * @ClassName: BaseFragment
 * @Author: Bubing
 * @Date: 2020/8/6 5:15 PM
 * @Description: 你项目中的Fragment基类，在这里实现显示弹窗，吐司，还有自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmFragment例如 abstract class BaseFragment<VM : BaseViewModel> : BaseVmFragment<VM>() {}
 */
abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding> :
    BaseVmVdbFragment<VM, VDB>() {

    //Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
    val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    //Application全局的ViewModel，用于发送全局通知操作
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {

    }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }
}