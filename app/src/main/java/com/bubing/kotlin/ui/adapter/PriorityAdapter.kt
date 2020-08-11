package com.bubing.kotlin.ui.adapter

import com.bubing.kotlin.R
import com.bubing.kotlin.data.model.enums.TodoType
import com.bubing.kotlin.weight.preference.MyColorCircleView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @ClassName: PriorityAdapter
 * @Author: Bubing
 * @Date: 2020/8/11 2:39 PM
 * @Description: 重要程度 adapter
 */
class PriorityAdapter(data: ArrayList<TodoType>) :
    BaseQuickAdapter<TodoType, BaseViewHolder>(R.layout.item_dialog_todo, data) {
    var checkType = TodoType.TodoType1.type

    constructor(data: ArrayList<TodoType>, checkType: Int) : this(data) {
        this.checkType = checkType
    }

    override fun convert(holder: BaseViewHolder, item: TodoType) {
        //赋值
        item.run {
            holder.setText(R.id.item_todo_dialog_name, item.content)
            if (checkType == item.type) {
                holder.getView<MyColorCircleView>(R.id.item_todo_dialog_icon)
                    .setViewSelect(item.color)
            } else {
                holder.getView<MyColorCircleView>(R.id.item_todo_dialog_icon).setView(item.color)
            }
        }
    }
}