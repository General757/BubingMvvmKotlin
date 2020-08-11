package com.bubing.kotlin.ui.fragment.todo

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.*
import com.bubing.kotlin.weight.recyclerview.SpaceItemDecoration
import com.bubing.kotlin.databinding.FragmentListBinding
import com.bubing.kotlin.ui.adapter.TodoAdapter
import com.bubing.kotlin.viewmodel.request.RequestTodoViewModel
import com.bubing.kotlin.viewmodel.state.TodoViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction

/**
 * @ClassName: TodoListFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:11 PM
 * @Description: java类作用描述
 */
class TodoListFragment : BaseFragment<TodoViewModel, FragmentListBinding>() {

    //适配器
    private val articleAdapter: TodoAdapter by lazy { TodoAdapter(arrayListOf()) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //请求数据ViewModel
    private val requestViewModel: RequestTodoViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_list

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.run {
            initClose("TODO") {
                nav().navigateUp()
            }
            inflateMenu(R.menu.menu_todo)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.todo_add -> {
                        nav().navigateAction(R.id.action_todoListFragment_to_addTodoFragment)
                    }
                }
                true
            }
        }
        //状态页配置 swipeRefresh参数表示你要包裹的布局
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击错误重试时触发的操作
            loadsir.showLoading()
            //请求数据
            requestViewModel.getTodoData(true)
        }

        //初始化recyclerView
        swipeRecyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                requestViewModel.getTodoData(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatingActionButton)
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            requestViewModel.getTodoData(true)
        }
        articleAdapter.run {
            setOnItemClickListener { _, _, position ->
                nav().navigateAction(R.id.action_todoListFragment_to_addTodoFragment,
                    Bundle().apply {
                        putParcelable("todo", articleAdapter.data[position])
                    })
            }
            addChildClickViewIds(R.id.item_todo_setting)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_todo_setting -> {
                        val items = if (articleAdapter.data[position].isDone()) {
                            listOf("删除", "编辑")
                        } else {
                            listOf("删除", "编辑", "完成")
                        }
                        activity?.let { activity ->
                            MaterialDialog(activity, BottomSheet())
                                .lifecycleOwner(viewLifecycleOwner).show {
                                    cornerRadius(8f)
                                    setPeekHeight(ConvertUtils.dp2px((items.size * 50 + 36).toFloat()))
                                    listItems(items = items) { _, index, item ->
                                        when (index) {
                                            0 -> {
                                                //删除
                                                requestViewModel.delTodo(
                                                    articleAdapter.data[position].id,
                                                    position
                                                )
                                            }
                                            1 -> {
                                                //编辑
                                                nav().navigateAction(R.id.action_todoListFragment_to_addTodoFragment,
                                                    Bundle().apply {
                                                        putParcelable(
                                                            "todo",
                                                            articleAdapter.data[position]
                                                        )
                                                    })
                                            }
                                            2 -> {
                                                //完成
                                                requestViewModel.doneTodo(
                                                    articleAdapter.data[position].id,
                                                    position
                                                )
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestViewModel.getTodoData(true)
    }

    override fun createObserver() {
        requestViewModel.todoDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadsir, swipeRecyclerView, swipeRefreshLayout)
        })
        requestViewModel.delDataState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                if (articleAdapter.data.size == 1) {
                    loadsir.showEmpty()
                }
                articleAdapter.remove(it.data!!)
            } else {
                showMessage(it.errorMsg)
            }
        })
        requestViewModel.doneDataState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                swipeRefreshLayout.isRefreshing = true
                requestViewModel.getTodoData(true)
            } else {
                showMessage(it.errorMsg)
            }
        })

        eventViewModel.todoEvent.observe(viewLifecycleOwner, Observer {
            if (articleAdapter.data.size == 0) {
                //界面没有数据时，变为加载中 增强一丢丢体验
                loadsir.showLoading()
            } else {
                //有数据时，swipeRefresh 显示刷新状态
                swipeRefreshLayout.isRefreshing = true
            }
            requestViewModel.getTodoData(true)
        })

    }

}