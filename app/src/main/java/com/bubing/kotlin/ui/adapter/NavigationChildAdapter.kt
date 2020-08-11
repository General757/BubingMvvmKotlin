package com.bubing.kotlin.ui.adapter

import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.util.ColorUtil
import com.bubing.kotlin.util.SettingUtil
import com.bubing.mvvmk.ext.util.toHtml
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: NavigationChildAdapter
 * @Author: Bubing
 * @Date: 2020/8/10 1:56 PM
 * @Description: java类作用描述
 */
class NavigationChildAdapter(data: ArrayList<AriticleResponse>) :
    BaseQuickAdapter<AriticleResponse, BaseViewHolder>(R.layout.flow_layout, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: AriticleResponse) {
        holder.setText(R.id.flow_tag, item.title.toHtml())
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}