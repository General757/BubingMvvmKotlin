package com.bubing.kotlin.data.model.bean

import java.io.Serializable

/**
 * @ClassName: ApiPagerResponse
 * @Author: Bubing
 * @Date: 2020/8/7 3:32 PM
 * @Description: 分页数据的基类
 */
data class ApiPagerResponse<T>(
    var datas: T,
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
) : Serializable {
    /**
     * 数据是否为空
     */
    fun isEmpty(): Boolean {

        return (datas as List<*>).size == 0
    }

    /**
     * 是否为刷新
     */
    fun isRefresh(): Boolean {
        //wanandroid 第一页该字段都为0
        return offset == 0
    }

    /**
     * 是否还有更多数据
     */
    fun hasMore(): Boolean {
        return !over
    }
}