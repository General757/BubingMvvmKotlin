package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.network.callback.ListDataUiState
import com.bubing.kotlin.network.callback.UpdateUiState
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request
import com.bubing.mvvmk.state.ResultState

/**
 * @ClassName: RequestAriticleViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:15 PM
 * @Description: 只做一件事，拿数据源
 */
class RequestAriticleViewModel : BaseViewModel() {

    var pageNo = 0

    var addData = MutableLiveData<ResultState<Any?>>()

    //分享的列表集合数据
    var shareDataState = MutableLiveData<ListDataUiState<AriticleResponse>>()

    //删除分享文章回调数据
    var delDataState = MutableLiveData<UpdateUiState<Int>>()

    fun addAriticle(shareTitle: String, shareUrl: String) {
        request(
            { apiService.addAriticle(shareTitle, shareUrl) },
            addData,
            true,
            "正在分享文章中..."
        )
    }

    fun getShareData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ apiService.getShareData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.shareArticles.isEmpty(),
                    hasMore = it.shareArticles.hasMore(),
                    isFirstEmpty = isRefresh && it.shareArticles.isEmpty(),
                    listData = it.shareArticles.datas
                )
            shareDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            shareDataState.postValue(listDataUiState)
        })
    }

    fun deleteShareData(id: Int, position: Int) {
        request({ apiService.deleteShareData(id) }, {
            val updateUiState = UpdateUiState(isSuccess = true, data = position)
            delDataState.postValue(updateUiState)
        }, {
            val updateUiState =
                UpdateUiState(isSuccess = false, data = position, errorMsg = it.errorMsg)
            delDataState.postValue(updateUiState)
        })
    }
}