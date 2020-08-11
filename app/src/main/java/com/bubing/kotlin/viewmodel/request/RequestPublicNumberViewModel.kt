package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.data.model.bean.ClassifyResponse
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.network.callback.ListDataUiState
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request
import com.bubing.mvvmk.state.ResultState

/**
 * @ClassName: RequestPublicNumberViewModel
 * @Author: Bubing
 * @Date: 2020/8/7 5:37 PM
 * @Description: java类作用描述
 */
class RequestPublicNumberViewModel : BaseViewModel() {

    var pageNo = 1

    var titleData: MutableLiveData<ResultState<ArrayList<ClassifyResponse>>> = MutableLiveData()

    var publicDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()


    fun getPublicTitleData() {
        request({ apiService.getPublicTitle() }, titleData)
    }

    fun getPublicData(isRefresh: Boolean, cid: Int) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getPublicData(pageNo, cid) }, {
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
            publicDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            publicDataState.postValue(listDataUiState)
        })
    }
}