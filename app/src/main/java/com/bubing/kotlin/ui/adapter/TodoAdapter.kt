package com.bubing.kotlin.ui.adapter

import android.util.TypedValue
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.bubing.kotlin.R
import com.bubing.kotlin.app.ext.setAdapterAnimation
import com.bubing.kotlin.data.model.bean.TodoResponse
import com.bubing.kotlin.data.model.enums.TodoType
import com.bubing.kotlin.util.SettingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: TodoAdapter
 * @Author: Bubing
 * @Date: 2020/8/11 2:42 PM
 * @Description: 积分排行 adapter
 */
class TodoAdapter(data: ArrayList<TodoResponse>) :
    BaseQuickAdapter<TodoResponse, BaseViewHolder>(R.layout.item_adapter_todo, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: TodoResponse) {
        //赋值
        item.run {
            holder.setText(R.id.item_todo_title, title)
            holder.setText(R.id.item_todo_content, content)
            holder.setText(R.id.item_todo_date, dateStr)
            if (status == 1) {
                //已完成
                holder.setVisible(R.id.item_todo_status, true)
                holder.setImageResource(R.id.item_todo_status, R.drawable.ic_done)
                holder.getView<CardView>(R.id.item_todo_cardview).foreground =
                    context.getDrawable(R.drawable.forground_shap)
            } else {
                if (isDone()) {
                    //未完成并且超过了预定完成时间
                    holder.setVisible(R.id.item_todo_status, true)
                    holder.setImageResource(R.id.item_todo_status, R.drawable.ic_yiguoqi)
                    holder.getView<CardView>(R.id.item_todo_cardview).foreground =
                        context.getDrawable(R.drawable.forground_shap)
                } else {
                    //未完成
                    holder.setVisible(R.id.item_todo_status, false)
                    TypedValue().apply {
                        context.theme.resolveAttribute(R.attr.selectableItemBackground, this, true)
                    }.run {
                        holder.getView<CardView>(R.id.item_todo_cardview).foreground =
                            context.getDrawable(resourceId)
                    }
                }
            }
            holder.getView<ImageView>(R.id.item_todo_tag).imageTintList =
                SettingUtil.getOneColorStateList(
                    TodoType.byType(priority).color
                )
        }
    }
}