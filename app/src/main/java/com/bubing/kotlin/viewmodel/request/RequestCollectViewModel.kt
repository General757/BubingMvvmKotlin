package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.CollectResponse
import com.bubing.kotlin.data.model.bean.CollectUrlResponse
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.network.callback.CollectUiState
import com.bubing.kotlin.network.callback.ListDataUiState
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request

/**
 * @ClassName: RequestCollectViewModel
 * @Author: Bubing
 * @Date: 2020/8/7 2:17 PM
 * @Description: 专门为了收藏而写的ViewModel
 */
open class RequestCollectViewModel : BaseViewModel() {

    private var pageNo = 0

    //收藏文章
    val collectUiState: MutableLiveData<CollectUiState> = MutableLiveData()

    //收藏网址
    val collectUrlUiState: MutableLiveData<CollectUiState> = MutableLiveData()

    //收藏de文章数据
    var ariticleDataState: MutableLiveData<ListDataUiState<CollectResponse>> = MutableLiveData()

    //收藏de网址数据
    var urlDataState: MutableLiveData<ListDataUiState<CollectUrlResponse>> = MutableLiveData()

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun collect(id: Int) {
        request({ apiService.collect(id) }, {
            val uiState = CollectUiState(
                isSuccess = true,
                collect = true,
                id = id
            )
            collectUiState.postValue(uiState)
        }, {
            val uiState =
                CollectUiState(
                    isSuccess = false,
                    collect = false,
                    errorMsg = it.errorMsg,
                    id = id
                )
            collectUiState.postValue(uiState)
        })
    }

    /**
     * 取消收藏文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun uncollect(id: Int) {
        request({ apiService.uncollect(id) }, {
            val uiState = CollectUiState(
                isSuccess = true,
                collect = false,
                id = id
            )
            collectUiState.postValue(uiState)
        }, {
            val uiState =
                CollectUiState(
                    isSuccess = false,
                    collect = true,
                    errorMsg = it.errorMsg,
                    id = id
                )
            collectUiState.postValue(uiState)
        })
    }

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun collectUrl(name: String, link: String) {
        request({ apiService.collectUrl(name, link) }, {
            val uiState = CollectUiState(
                isSuccess = true,
                collect = true,
                id = it.id
            )
            collectUrlUiState.postValue(uiState)
        }, {
            val uiState =
                CollectUiState(
                    isSuccess = false,
                    collect = false,
                    errorMsg = it.errorMsg,
                    id = 0
                )
            collectUrlUiState.postValue(uiState)
        })
    }

    /**
     * 取消收藏网址
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun uncollectUrl(id: Int) {
        request({ apiService.uncollect(id) }, {
            val uiState = CollectUiState(
                isSuccess = true,
                collect = false,
                id = id
            )
            collectUrlUiState.postValue(uiState)
        }, {
            val uiState =
                CollectUiState(
                    isSuccess = false,
                    collect = true,
                    errorMsg = it.errorMsg,
                    id = id
                )
            collectUrlUiState.postValue(uiState)
        })
    }

    fun getCollectAriticleData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ apiService.getCollectData(pageNo) }, {
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
            ariticleDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<CollectResponse>()
                )
            ariticleDataState.postValue(listDataUiState)
        })
    }

    fun getCollectUrlData() {
        request({ apiService.getCollectUrlData() }, {
            //请求成功
            val listDataUiState =
                ListDataUiState(
                    isRefresh = true,
                    isSuccess = true,
                    hasMore = false,
                    isEmpty = it.isEmpty(),
                    listData = it
                )
            urlDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    listData = arrayListOf<CollectUrlResponse>()
                )
            urlDataState.postValue(listDataUiState)
        })
    }
}