package com.bubing.mvvmk.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @ClassName: StringLiveData
 * @Author: Bubing
 * @Date: 2020/8/7 5:43 PM
 * @Description: 自定义的String类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class StringLiveData : MutableLiveData<String>() {

    override fun getValue(): String {
        return super.getValue() ?: ""
    }

}