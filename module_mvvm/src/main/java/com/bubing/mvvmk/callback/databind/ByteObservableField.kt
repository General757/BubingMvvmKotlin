package com.bubing.mvvmk.callback.databind

import androidx.databinding.ObservableField

/**
 * @ClassName: ByteObservableField
 * @Author: Bubing
 * @Date: 2020/8/10 3:04 PM
 * @Description: 自定义的 Byte 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class ByteObservableField(value: Byte = 0) : ObservableField<Byte>(value) {

    override fun get(): Byte {
        return super.get()!!
    }

}