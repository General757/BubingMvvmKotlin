package com.bubing.kotlin.weight.callback

import android.content.Context
import android.view.View
import com.bubing.kotlin.R
import com.kingja.loadsir.callback.Callback

/**
 * @ClassName: LoadingCallback
 * @Author: Bubing
 * @Date: 2020/8/6 3:12 PM
 * @Description: 加载view
 */
class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}