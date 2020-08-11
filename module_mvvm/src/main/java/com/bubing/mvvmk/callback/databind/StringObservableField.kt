package com.bubing.mvvmk.callback.databind

import androidx.databinding.ObservableField

/**
 * @ClassName: StringObservableField
 * @Author: Bubing
 * @Date: 2020/8/7 5:42 PM
 * @Description: 自定义的String类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class StringObservableField(value: String = "") : ObservableField<String>(value) {

    override fun get(): String {
        return super.get()!!
    }

}