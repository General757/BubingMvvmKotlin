package com.bubing.kotlin.viewmodel.state

import com.bubing.kotlin.data.model.enums.TodoType
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.callback.databind.IntObservableField
import com.bubing.mvvmk.callback.databind.StringObservableField

/**
 * @ClassName: TodoViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:24 PM
 * @Description: java类作用描述
 */
class TodoViewModel : BaseViewModel() {

    //标题
    var todoTitle = StringObservableField()

    //内容
    var todoContent =
        StringObservableField()

    //时间
    var todoTime = StringObservableField()

    //优先级
    var todoLeve =
        StringObservableField(TodoType.TodoType1.content)

    //优先级颜色
    var todoColor =
        IntObservableField(TodoType.TodoType1.color)

}