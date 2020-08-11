package com.bubing.kotlin.ui.adapter

import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.data.model.bean.IntegralHistoryResponse
import com.bubing.kotlin.util.DatetimeUtil
import com.bubing.kotlin.util.SettingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: IntegralHistoryAdapter
 * @Author: Bubing
 * @Date: 2020/8/11 2:38 PM
 * @Description: 积分获取历史 adapter
 */
class IntegralHistoryAdapter(data: ArrayList<IntegralHistoryResponse>) :
    BaseQuickAdapter<IntegralHistoryResponse, BaseViewHolder>(
        R.layout.item_adapter_integral_history, data
    ) {
    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: IntegralHistoryResponse) {
        //赋值
        item.run {
            val descStr =
                if (desc.contains("积分")) desc.subSequence(desc.indexOf("积分"), desc.length) else ""
            holder.setText(R.id.item_integralhistory_title, reason + descStr)
            holder.setText(
                R.id.item_integralhistory_date,
                DatetimeUtil.formatDate(date, DatetimeUtil.DATE_PATTERN_SS)
            )
            holder.setText(R.id.item_integralhistory_count, "+$coinCount")
            holder.setTextColor(R.id.item_integralhistory_count, SettingUtil.getColor(context))
        }
    }
}