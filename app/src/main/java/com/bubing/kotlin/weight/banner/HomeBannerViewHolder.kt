package com.bubing.kotlin.weight.banner

import android.view.View
import android.widget.ImageView
import com.bubing.kotlin.R
import com.bubing.kotlin.data.model.bean.BannerResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zhpan.bannerview.BaseViewHolder

/**
 * @ClassName: HomeBannerViewHolder
 * @Author: Bubing
 * @Date: 2020/8/7 5:13 PM
 * @Description: java类作用描述
 */
class HomeBannerViewHolder(view: View) : BaseViewHolder<BannerResponse>(view) {
    override fun bindData(data: BannerResponse?, position: Int, pageSize: Int) {
        val img = itemView.findViewById<ImageView>(R.id.bannerhome_img)
        data?.let {
            Glide.with(img.context.applicationContext)
                .load(it.imagePath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(img)
        }
    }
}