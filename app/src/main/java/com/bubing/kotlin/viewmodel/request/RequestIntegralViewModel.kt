package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.IntegralHistoryResponse
import com.bubing.kotlin.data.model.bean.IntegralResponse
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.network.callback.ListDataUiState
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request

/**
 * @ClassName: RequestIntegralViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:16 PM
 * @Description: java类作用描述
 */
class RequestIntegralViewModel : BaseViewModel() {

    private var pageNo = 1

    //积分排行数据
    var integralDataState = MutableLiveData<ListDataUiState<IntegralResponse>>()

    //获取积分历史数据
    var integralHistoryDataState = MutableLiveData<ListDataUiState<IntegralHistoryResponse>>()

    fun getIntegralData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getIntegralRank(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            integralDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralResponse>()
                )
            integralDataState.postValue(listDataUiState)
        })
    }

    fun getIntegralHistoryData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getIntegralHistory(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            integralHistoryDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<IntegralHistoryResponse>()
                )
            integralHistoryDataState.postValue(listDataUiState)
        })
    }
}