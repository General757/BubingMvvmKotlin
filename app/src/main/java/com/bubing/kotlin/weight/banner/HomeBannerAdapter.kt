package com.bubing.kotlin.weight.banner

import android.view.View
import com.bubing.kotlin.R
import com.bubing.kotlin.data.model.bean.BannerResponse
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * @ClassName: HomeBannerAdapter
 * @Author: Bubing
 * @Date: 2020/8/7 5:13 PM
 * @Description: java类作用描述
 */
class HomeBannerAdapter : BaseBannerAdapter<BannerResponse, HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner_home
    }

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView);
    }

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: BannerResponse?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }
}