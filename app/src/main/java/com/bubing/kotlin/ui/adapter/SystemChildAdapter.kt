package com.bubing.kotlin.ui.adapter

import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.data.model.bean.ClassifyResponse
import com.bubing.kotlin.util.ColorUtil
import com.bubing.kotlin.util.SettingUtil
import com.bubing.mvvmk.ext.util.toHtml
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: SystemChildAdapter
 * @Author: Bubing
 * @Date: 2020/8/10 1:53 PM
 * @Description: java类作用描述
 */

class SystemChildAdapter(data: ArrayList<ClassifyResponse>) :
    BaseQuickAdapter<ClassifyResponse, BaseViewHolder>(R.layout.flow_layout, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: ClassifyResponse) {
        holder.setText(R.id.flow_tag, item.name.toHtml())
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}