package com.bubing.kotlin.ui.adapter

import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.data.model.bean.SearchResponse
import com.bubing.kotlin.util.ColorUtil
import com.bubing.kotlin.util.SettingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: SearcHotAdapter
 * @Author: Bubing
 * @Date: 2020/8/11 2:40 PM
 * @Description: 搜索热门 adapter
 */
class SearcHotAdapter(data: ArrayList<SearchResponse>) :
    BaseQuickAdapter<SearchResponse, BaseViewHolder>(R.layout.flow_layout, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: SearchResponse) {
        holder.setText(R.id.flow_tag, item.name)
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}