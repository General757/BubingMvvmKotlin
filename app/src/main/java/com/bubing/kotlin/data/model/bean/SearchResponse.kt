package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: SearchResponse
 * @Author: Bubing
 * @Date: 2020/8/10 2:36 PM
 * @Description: 搜索热词
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class SearchResponse(
    var id: Int,
    var link: String,
    var name: String,
    var order: Int,
    var visible: Int
) : Parcelable