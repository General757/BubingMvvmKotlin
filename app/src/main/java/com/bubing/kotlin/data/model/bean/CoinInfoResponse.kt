package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: CoinInfoResponse
 * @Author: Bubing
 * @Date: 2020/8/10 2:37 PM
 * @Description: 分享人信息
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CoinInfoResponse(
    var coinCount: Int,
    var rank: String,
    var userId: Int,
    var username: String
) : Parcelable