package com.bubing.kotlin.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @ClassName: ShareResponse
 * @Author: Bubing
 * @Date: 2020/8/10 2:36 PM
 * @Description: java类作用描述
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ShareResponse(
    var coinInfo: CoinInfoResponse,
    var shareArticles: ApiPagerResponse<ArrayList<AriticleResponse>>
) : Parcelable