package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.IntegralResponse
import com.bubing.kotlin.network.apiService
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request
import com.bubing.mvvmk.state.ResultState

/**
 * @ClassName: RequestMeViewModel
 * @Author: Bubing
 * @Date: 2020/8/7 5:44 PM
 * @Description: java类作用描述
 */
class RequestMeViewModel : BaseViewModel() {

    var meData = MutableLiveData<ResultState<IntegralResponse>>()

    fun getIntegral() {
        request({ apiService.getIntegral() }, meData)
    }
}