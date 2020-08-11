package com.bubing.mvvmk.ext.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bubing.mvvmk.callback.livedata.BooleanLiveData

/**
 * @ClassName: KtxAppLifeObserver
 * @Author: Bubing
 * @Date: 2020/8/6 2:45 PM
 * @Description: java类作用描述
 */
object KtxAppLifeObserver : LifecycleObserver {

    var isForeground = BooleanLiveData()

    //在前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onForeground() {
        isForeground.value = true
    }

    //在后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        isForeground.value = false
    }

}