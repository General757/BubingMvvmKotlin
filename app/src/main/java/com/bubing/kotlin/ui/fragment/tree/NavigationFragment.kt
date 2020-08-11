package com.bubing.kotlin.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.*
import com.bubing.kotlin.weight.recyclerview.SpaceItemDecoration
import com.bubing.kotlin.databinding.IncludeListBinding
import com.bubing.kotlin.ui.adapter.NavigationAdapter
import com.bubing.kotlin.viewmodel.request.RequestTreeViewModel
import com.bubing.kotlin.viewmodel.state.TreeViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction

/**
 * @ClassName: NavigationFragment
 * @Author: Bubing
 * @Date: 2020/8/10 1:40 PM
 * @Description: 体系
 */
class NavigationFragment : BaseFragment<TreeViewModel, IncludeListBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId() = R.layout.include_list

    private val navigationAdapter: NavigationAdapter by lazy { NavigationAdapter(arrayListOf()) }

    /** */
    private val requestTreeViewModel: RequestTreeViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestTreeViewModel.getNavigationData()
        }
        //初始化recyclerView
        swipeRecyclerView.init(LinearLayoutManager(context), navigationAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFloatBtn(floatingActionButton)
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            requestTreeViewModel.getNavigationData()
        }
        navigationAdapter.setNavigationAction { item, view ->
            nav().navigateAction(R.id.action_to_webFragment,
                Bundle().apply {
                    putParcelable("ariticleData", item)
                }
            )
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestTreeViewModel.getNavigationData()
    }

    override fun createObserver() {
        requestTreeViewModel.navigationDataState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            if (it.isSuccess) {
                loadsir.showSuccess()
                navigationAdapter.setList(it.listData)
            } else {
                loadsir.showError(it.errMessage)
            }
        })
        appViewModel.run {
            //监听全局的主题颜色改变
            appColor.observe(viewLifecycleOwner, Observer {
                setUiTheme(it, floatingActionButton, swipeRefreshLayout, loadsir)
            })
            //监听全局的列表动画改编
            appAnimation.observe(viewLifecycleOwner, Observer {
                navigationAdapter.setAdapterAnimation(it)
            })
        }

    }
}