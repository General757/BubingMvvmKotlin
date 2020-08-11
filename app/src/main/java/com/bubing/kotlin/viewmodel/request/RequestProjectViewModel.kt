package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.data.model.bean.ClassifyResponse
import com.bubing.kotlin.data.repository.request.HttpRequestCoroutine
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.network.callback.ListDataUiState
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request
import com.bubing.mvvmk.state.ResultState

/**
 * @ClassName: RequestProjectViewModel
 * @Author: Bubing
 * @Date: 2020/8/7 5:34 PM
 * @Description: java类作用描述
 */
class RequestProjectViewModel : BaseViewModel() {

    //页码
    var pageNo = 1

    var titleData: MutableLiveData<ResultState<ArrayList<ClassifyResponse>>> = MutableLiveData()

    var projectDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()

    fun getProjectTitleData() {
        request({ apiService.getProjecTitle() }, titleData)
    }

    fun getProjectData(isRefresh: Boolean, cid: Int, isNew: Boolean = false) {
        if (isRefresh) {
            pageNo = if (isNew) 0 else 1
        }
        request({ HttpRequestCoroutine.getProjectData(pageNo, cid, isNew) }, {
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
            projectDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            projectDataState.postValue(listDataUiState)
        })
    }
}