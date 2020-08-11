package com.bubing.kotlin.weight.callback

import com.bubing.kotlin.R
import com.kingja.loadsir.callback.Callback

/**
 * @ClassName: EmptyCallback
 * @Author: Bubing
 * @Date: 2020/8/6 4:37 PM
 * @Description: 空view
 */
class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }
}