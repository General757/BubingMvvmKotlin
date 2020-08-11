package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: IntegralHistoryResponse
 * @Author: Bubing
 * @Date: 2020/8/10 2:38 PM
 * @Description: 积分记录
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class IntegralHistoryResponse(
    var coinCount: Int,
    var date: Long,
    var desc: String,
    var id: Int,
    var type: Int,
    var reason: String,
    var userId: Int,
    var userName: String
) : Parcelable