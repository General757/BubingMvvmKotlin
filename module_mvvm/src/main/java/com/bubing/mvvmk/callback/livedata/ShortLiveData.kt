package com.bubing.mvvmk.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @ClassName: ShortLiveData
 * @Author: Bubing
 * @Date: 2020/8/10 3:09 PM
 * @Description: 自定义的Short类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class ShortLiveData : MutableLiveData<Short>() {
    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}