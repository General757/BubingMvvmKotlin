package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: TagsResponse
 * @Author: Bubing
 * @Date: 2020/8/7 2:00 PM
 * @Description: 文章的标签
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class TagsResponse(var name: String, var url: String) : Parcelable