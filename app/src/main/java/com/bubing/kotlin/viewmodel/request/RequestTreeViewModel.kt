package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.data.model.bean.NavigationResponse
import com.bubing.kotlin.data.model.bean.SystemResponse
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.network.callback.ListDataUiState
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request

/**
 * @ClassName: RequestTreeViewModel
 * @Author: Bubing
 * @Date: 2020/8/10 1:45 PM
 * @Description: java类作用描述
 */
class RequestTreeViewModel : BaseViewModel() {

    //页码，体系 广场的页码是从0开始的
    private var pageNo = 0

    //广场数据
    var plazaDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()

    //每日一问数据
    var askDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()

    //体系子栏目列表数据
    var systemChildDataState: MutableLiveData<ListDataUiState<AriticleResponse>> = MutableLiveData()

    //体系数据
    var systemDataState: MutableLiveData<ListDataUiState<SystemResponse>> = MutableLiveData()

    //导航数据
    var navigationDataState: MutableLiveData<ListDataUiState<NavigationResponse>> =
        MutableLiveData()

    /**
     * 获取广场数据
     */
    fun getPlazaData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ apiService.getSquareData(pageNo) }, {
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
            plazaDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            plazaDataState.postValue(listDataUiState)
        })
    }

    /**
     * 获取每日一问数据
     */
    fun getAskData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1 //每日一问的页码从1开始
        }
        request({ apiService.getAskData(pageNo) }, {
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
            askDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            askDataState.postValue(listDataUiState)
        })
    }

    /**
     * 获取体系数据
     */
    fun getSystemData() {
        request({ apiService.getSystemData() }, {
            //请求成功
            val dataUiState =
                ListDataUiState(
                    isSuccess = true,
                    listData = it
                )
            systemDataState.postValue(dataUiState)
        }, {
            //请求失败
            val dataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    listData = arrayListOf<SystemResponse>()
                )
            systemDataState.postValue(dataUiState)
        })
    }

    /**
     * 获取导航数据
     */
    fun getNavigationData() {
        request({ apiService.getNavigationData() }, {
            //请求成功
            val dataUiState =
                ListDataUiState(
                    isSuccess = true,
                    listData = it
                )
            navigationDataState.postValue(dataUiState)
        }, {
            //请求失败
            val dataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    listData = arrayListOf<NavigationResponse>()
                )
            navigationDataState.postValue(dataUiState)
        })
    }

    /**
     * 获取体系子栏目列表数据
     */
    fun getSystemChildData(isRefresh: Boolean, cid: Int) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ apiService.getSystemChildData(pageNo, cid) }, {
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
            systemChildDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            plazaDataState.postValue(listDataUiState)
        })
    }


}