package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.ApiPagerResponse
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.data.model.bean.SearchResponse
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.util.CacheUtil
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.launch
import com.bubing.mvvmk.ext.request
import com.bubing.mvvmk.state.ResultState

/**
 * @ClassName: RequestSearchViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:18 PM
 * @Description: java类作用描述
 */
class RequestSearchViewModel : BaseViewModel() {

    var pageNo = 0

    //搜索热词数据
    var hotData: MutableLiveData<ResultState<ArrayList<SearchResponse>>> = MutableLiveData()

    //搜索结果数据
    var seachResultData: MutableLiveData<ResultState<ApiPagerResponse<ArrayList<AriticleResponse>>>> =
        MutableLiveData()

    //搜索历史词数据
    var historyData: MutableLiveData<ArrayList<String>> = MutableLiveData()

    /**
     * 获取热门数据
     */
    fun getHotData() {
        request({ apiService.getSearchData() }, hotData)
    }

    /**
     * 获取历史数据
     */
    fun getHistoryData() {
        launch({
            CacheUtil.getSearchHistoryData()
        }, {
            historyData.postValue(it)
        }, {
            //获取本地历史数据出异常了
        })
    }

    /**
     * 根据字符串搜索结果
     */
    fun getSearchResultData(searchKey: String, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request(
            { apiService.getSearchDataByKey(pageNo, searchKey) },
            seachResultData
        )
    }
}