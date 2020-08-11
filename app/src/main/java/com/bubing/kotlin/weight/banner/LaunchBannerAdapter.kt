package com.bubing.kotlin.weight.banner

import android.view.View
import com.bubing.kotlin.R
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * @ClassName: LaunchBannerAdapter
 * @Author: Bubing
 * @Date: 2020/8/6 3:51 PM
 * @Description: java类作用描述
 */
class LaunchBannerAdapter : BaseBannerAdapter<String, LaunchBannerViewHolder>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner_launch
    }

    override fun createViewHolder(itemView: View, viewType: Int): LaunchBannerViewHolder {
        return LaunchBannerViewHolder(itemView);
    }

    override fun onBind(
        holder: LaunchBannerViewHolder?,
        data: String?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }
}