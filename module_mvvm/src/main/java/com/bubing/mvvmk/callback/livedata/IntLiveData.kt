package com.bubing.mvvmk.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @ClassName: IntLiveData
 * @Author: Bubing
 * @Date: 2020/8/10 3:09 PM
 * @Description: 自定义的Boolean类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class IntLiveData : MutableLiveData<Int>() {

    override fun getValue(): Int {
        return super.getValue() ?: 0
    }
}