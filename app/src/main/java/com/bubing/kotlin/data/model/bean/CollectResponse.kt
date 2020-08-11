package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: CollectResponse
 * @Author: Bubing
 * @Date: 2020/8/7 5:08 PM
 * @Description: java类作用描述
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class CollectResponse(
    var chapterId: Int,
    var author: String,
    var chapterName: String,
    var courseId: Int,
    var desc: String,
    var envelopePic: String,
    var id: Int,
    var link: String,
    var niceDate: String,
    var origin: String,
    var originId: Int,
    var publishTime: Long,
    var title: String,
    var userId: Int,
    var visible: Int,
    var zan: Int
) : Parcelable