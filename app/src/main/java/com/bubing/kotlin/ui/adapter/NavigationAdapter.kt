package com.bubing.kotlin.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.util.SettingUtil
import com.bubing.kotlin.data.model.bean.AriticleResponse
import com.bubing.kotlin.data.model.bean.NavigationResponse
import com.bubing.mvvmk.ext.util.toHtml

/**
 * @ClassName: NavigationAdapter
 * @Author: Bubing
 * @Date: 2020/8/10 1:55 PM
 * @Description: java类作用描述
 */
class NavigationAdapter(data: ArrayList<NavigationResponse>) :
    BaseQuickAdapter<NavigationResponse, BaseViewHolder>(R.layout.item_system, data) {

    private var navigationAction: (item: AriticleResponse, view: View) -> Unit =
        { _: AriticleResponse, _: View -> }

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: NavigationResponse) {
        holder.setText(R.id.item_system_title, item.name.toHtml())
        holder.getView<RecyclerView>(R.id.item_system_rv).run {
            val foxLayoutManager: FlexboxLayoutManager by lazy {
                FlexboxLayoutManager(context).apply {
                    //方向 主轴为水平方向，起点在左端
                    flexDirection = FlexDirection.ROW
                    //左对齐
                    justifyContent = JustifyContent.FLEX_START
                }
            }
            layoutManager = foxLayoutManager
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = NavigationChildAdapter(item.articles).apply {
                setOnItemClickListener { _, view, position ->
                    navigationAction.invoke(item.articles[position], view)
                }
            }
        }
    }

    fun setNavigationAction(inputNavigationAction: (item: AriticleResponse, view: View) -> Unit) {
        this.navigationAction = inputNavigationAction
    }
}