package com.bubing.kotlin.ui.adapter

import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.data.model.bean.CollectUrlResponse
import com.bubing.kotlin.util.SettingUtil
import com.bubing.kotlin.weight.customview.CollectView
import com.bubing.mvvmk.ext.util.toHtml
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: CollectUrlAdapter
 * @Author: Bubing
 * @Date: 2020/8/11 2:37 PM
 * @Description: 收藏URL adapter
 */
class CollectUrlAdapter(data: ArrayList<CollectUrlResponse>) :
    BaseQuickAdapter<CollectUrlResponse, BaseViewHolder>(
        R.layout.item_adapter_collecturl, data
    ) {

    private var collectAction: (item: CollectUrlResponse, v: CollectView, position: Int) -> Unit =
        { _: CollectUrlResponse, _: CollectView, _: Int -> }

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: CollectUrlResponse) {
        //赋值
        item.run {
            holder.setText(R.id.item_collecturl_name, name.toHtml())
            holder.setText(R.id.item_collecturl_link, link)
            holder.getView<CollectView>(R.id.item_collecturl_collect).isChecked = true
        }
        holder.getView<CollectView>(R.id.item_collecturl_collect)
            .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                override fun onClick(v: CollectView) {
                    collectAction.invoke(item, v, holder.adapterPosition)
                }
            })
    }

    fun setCollectClick(inputCollectAction: (item: CollectUrlResponse, v: CollectView, position: Int) -> Unit) {
        this.collectAction = inputCollectAction
    }
}