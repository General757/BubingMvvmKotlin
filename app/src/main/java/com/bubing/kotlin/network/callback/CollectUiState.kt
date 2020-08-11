package com.bubing.kotlin.network.callback

/**
 * @ClassName: CollectUiState
 * @Author: Bubing
 * @Date: 2020/8/7 5:06 PM
 * @Description: 收藏数据状态类
 */
data class CollectUiState(
    //请求是否成功
    var isSuccess: Boolean = true,
    //收藏
    var collect: Boolean = false,
    //收藏Id
    var id: Int = -1,
    //请求失败的错误信息
    var errorMsg: String = ""
)