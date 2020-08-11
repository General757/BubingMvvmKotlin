package com.bubing.kotlin.weight.banner

import android.view.View
import android.widget.TextView
import com.bubing.kotlin.R
import com.zhpan.bannerview.BaseViewHolder

/**
 * @ClassName: LaunchBannerViewHolder
 * @Author: Bubing
 * @Date: 2020/8/6 3:53 PM
 * @Description: java类作用描述
 */
class LaunchBannerViewHolder(view: View) : BaseViewHolder<String>(view) {
    override fun bindData(data: String?, position: Int, pageSize: Int) {
        val textView = findView<TextView>(R.id.banner_text)
        textView.text = data
    }
}