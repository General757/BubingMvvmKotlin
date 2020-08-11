package com.bubing.kotlin.ui.adapter

import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.util.SettingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: ShareAdapter
 * @Author: Bubing
 * @Date: 2020/8/11 2:41 PM
 * @Description: 分享的文章 adapter
 */
class ShareAdapter(data: ArrayList<AriticleResponse>) :
    BaseQuickAdapter<AriticleResponse, BaseViewHolder>(
        R.layout.item_adapter_share_ariticle, data
    ) {
    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(helper: BaseViewHolder, item: AriticleResponse) {
        //赋值
        item.run {
            helper.setText(R.id.item_share_title, title)
            helper.setText(R.id.item_share_date, niceDate)
        }
    }
}