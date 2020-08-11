package com.bubing.mvvmk.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @ClassName: ByteLiveData
 * @Author: Bubing
 * @Date: 2020/8/10 3:06 PM
 * @Description: 自定义的Byte类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}