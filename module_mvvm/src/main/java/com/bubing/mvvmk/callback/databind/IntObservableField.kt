package com.bubing.mvvmk.callback.databind

import androidx.databinding.ObservableField

/**
 * @ClassName: IntObservableField
 * @Author: Bubing
 * @Date: 2020/8/7 5:42 PM
 * @Description: 自定义的Int类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class IntObservableField(value: Int = 0) : ObservableField<Int>(value) {

    override fun get(): Int {
        return super.get()!!
    }

}