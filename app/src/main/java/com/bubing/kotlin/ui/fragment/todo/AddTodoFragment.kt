package com.bubing.kotlin.ui.fragment.todo
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.initClose
import com.bubing.kotlin.app.ext.showMessage
import com.bubing.kotlin.data.model.bean.TodoResponse
import com.bubing.kotlin.data.model.enums.TodoType
import com.bubing.kotlin.databinding.FragmentAddtodoBinding
import com.bubing.kotlin.util.DatetimeUtil
import com.bubing.kotlin.util.SettingUtil
import com.bubing.kotlin.viewmodel.request.RequestTodoViewModel
import com.bubing.kotlin.viewmodel.state.TodoViewModel
import com.bubing.kotlin.weight.customview.PriorityDialog
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.util.notNull
import kotlinx.android.synthetic.main.fragment_addtodo.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.util.*

/**
 * @ClassName: AddTodoFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:10 PM
 * @Description: java类作用描述
 */
class AddTodoFragment : BaseFragment<TodoViewModel, FragmentAddtodoBinding>() {

    private var todoResponse: TodoResponse? = null

    //请求数据ViewModel
    private val requestViewModel: RequestTodoViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_addtodo

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        arguments?.let {
            todoResponse = it.getParcelable("todo")
            todoResponse?.let { todo ->
                mViewModel.todoTitle.set(todo.title)
                mViewModel.todoContent.set(todo.content)
                mViewModel.todoTime.set(todo.dateStr)
                mViewModel.todoLeve.set(TodoType.byType(todo.priority).content)
                mViewModel.todoColor.set(TodoType.byType(todo.priority).color)
            }
        }
        toolbar.initClose(if (todoResponse == null) "添加TODO" else "修改TODO") {
            nav().navigateUp()
        }
        appViewModel.appColor.value?.let { SettingUtil.setShapColor(addtodoSubmit, it) }
    }

    override fun createObserver() {
        requestViewModel.updateDataState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                nav().navigateUp()
                eventViewModel.todoEvent.setValue(false)
            } else {
                showMessage(it.errorMsg)
            }
        })
    }

    inner class ProxyClick {
        /** 选择时间*/
        fun todoTime() {
            activity?.let {
                MaterialDialog(it)
                    .lifecycleOwner(this@AddTodoFragment).show {
                        cornerRadius(0f)
                        datePicker(minDate = Calendar.getInstance()) { dialog, date ->
                            mViewModel.todoTime.set(
                                DatetimeUtil.formatDate(
                                    date.time,
                                    DatetimeUtil.DATE_PATTERN
                                )
                            )
                        }
                    }
            }
        }

        /**选择类型*/
        fun todoType() {
            activity?.let {
                PriorityDialog(it, TodoType.byValue(mViewModel.todoLeve.get()).type).apply {
                    setPriorityInterface(object : PriorityDialog.PriorityInterface {
                        override fun onSelect(type: TodoType) {
                            mViewModel.todoLeve.set(type.content)
                            mViewModel.todoColor.set(type.color)
                        }
                    })
                }.show()
            }
        }

        /**提交*/
        fun submit() {
            when {
                mViewModel.todoTitle.get().isEmpty() -> {
                    showMessage("请填写标题")
                }
                mViewModel.todoContent.get().isEmpty() -> {
                    showMessage("请填写内容")
                }
                mViewModel.todoTime.get().isEmpty() -> {
                    showMessage("请选择预计完成时间")
                }
                else -> {
                    todoResponse.notNull({
                        showMessage("确认提交编辑吗？", positiveButtonText = "提交", positiveAction = {
                            requestViewModel.updateTodo(
                                todoResponse!!.id,
                                mViewModel.todoTitle.get(),
                                mViewModel.todoContent.get(),
                                mViewModel.todoTime.get(),
                                TodoType.byValue(mViewModel.todoLeve.get()).type
                            )
                        }, negativeButtonText = "取消")
                    }, {
                        showMessage("确认添加吗？", positiveButtonText = "添加", positiveAction = {
                            requestViewModel.addTodo(
                                mViewModel.todoTitle.get(),
                                mViewModel.todoContent.get(),
                                mViewModel.todoTime.get(),
                                TodoType.byValue(mViewModel.todoLeve.get()).type
                            )
                        }, negativeButtonText = "取消")
                    })
                }
            }
        }
    }

}