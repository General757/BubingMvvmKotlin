package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: CollectUrlResponse
 * @Author: Bubing
 * @Date: 2020/8/7 5:08 PM
 * @Description: 收藏的网址类
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CollectUrlResponse(
    var icon: String,
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var userId: Int,
    var visible: Int
) : Parcelable