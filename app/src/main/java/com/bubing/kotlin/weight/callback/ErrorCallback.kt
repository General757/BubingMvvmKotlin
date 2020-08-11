package com.bubing.kotlin.weight.callback

import com.bubing.kotlin.R
import com.kingja.loadsir.callback.Callback

/**
 * @ClassName: ErrorCallback
 * @Author: Bubing
 * @Date: 2020/8/6 4:37 PM
 * @Description: 错误view
 */
class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}