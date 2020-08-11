package com.bubing.mvvmk.callback.databind

import androidx.databinding.ObservableField

/**
 * @ClassName: DoubleObservableField
 * @Author: Bubing
 * @Date: 2020/8/10 3:04 PM
 * @Description: 自定义的Double类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class DoubleObservableField(value: Double = 0.0) : ObservableField<Double>(value) {

    override fun get(): Double {
        return super.get()!!
    }

}