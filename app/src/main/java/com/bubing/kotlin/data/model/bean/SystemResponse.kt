package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: SystemResponse
 * @Author: Bubing
 * @Date: 2020/8/10 1:48 PM
 * @Description: 体系数据
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class SystemResponse(
    var children: ArrayList<ClassifyResponse>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
) : Parcelable