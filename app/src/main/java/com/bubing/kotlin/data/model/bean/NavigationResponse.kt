package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: NavigationResponse
 * @Author: Bubing
 * @Date: 2020/8/10 1:47 PM
 * @Description: 导航数据
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class NavigationResponse(
    var articles: ArrayList<AriticleResponse>,
    var cid: Int,
    var name: String
) : Parcelable