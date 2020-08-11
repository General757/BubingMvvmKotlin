package com.bubing.mvvmk.callback.databind

import androidx.databinding.ObservableField

/**
 * @ClassName: ShortObservableField
 * @Author: Bubing
 * @Date: 2020/8/10 3:05 PM
 * @Description: 自定义的 Short 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class ShortObservableField(value: Short = 0) : ObservableField<Short>(value) {

    override fun get(): Short {
        return super.get()!!
    }

}