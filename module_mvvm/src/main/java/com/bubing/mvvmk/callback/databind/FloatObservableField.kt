package com.bubing.mvvmk.callback.databind

import androidx.databinding.ObservableField

/**
 * @ClassName: FloatObservableField
 * @Author: Bubing
 * @Date: 2020/8/10 3:05 PM
 * @Description: 自定义的 Float 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class FloatObservableField(value: Float = 0f) : ObservableField<Float>(value) {

    override fun get(): Float {
        return super.get()!!
    }

}