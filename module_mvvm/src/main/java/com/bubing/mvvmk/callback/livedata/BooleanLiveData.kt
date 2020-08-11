package com.bubing.mvvmk.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @ClassName: BooleanLiveData
 * @Author: Bubing
 * @Date: 2020/8/5 5:34 PM
 * @Description: 自定义的Boolean类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class BooleanLiveData : MutableLiveData<Boolean>() {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }
}