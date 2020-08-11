package com.bubing.kotlin.ui.fragment.integral

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.*
import com.bubing.kotlin.databinding.FragmentListBinding
import com.bubing.kotlin.ui.adapter.IntegralHistoryAdapter
import com.bubing.kotlin.viewmodel.request.RequestIntegralViewModel
import com.bubing.kotlin.viewmodel.state.IntegralViewModel
import com.bubing.kotlin.weight.recyclerview.SpaceItemDecoration
import com.bubing.mvvmk.ext.nav
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @ClassName: IntegralHistoryFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:01 PM
 * @Description: 积分记录排行
 */
class IntegralHistoryFragment : BaseFragment<IntegralViewModel, FragmentListBinding>() {

    //适配器
    private val integralAdapter: IntegralHistoryAdapter by lazy { IntegralHistoryAdapter(arrayListOf()) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //请求的ViewModel /** */
    private val requestIntegralViewModel: RequestIntegralViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_list

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("积分记录") {
            nav().navigateUp()
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestIntegralViewModel.getIntegralHistoryData(true)
        }
        //初始化recyclerView
        swipeRecyclerView.init(LinearLayoutManager(context), integralAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                requestIntegralViewModel.getIntegralHistoryData(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatingActionButton)
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            requestIntegralViewModel.getIntegralHistoryData(true)
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestIntegralViewModel.getIntegralHistoryData(true)
    }

    override fun createObserver() {
        requestIntegralViewModel.integralHistoryDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, integralAdapter, loadsir, swipeRecyclerView, swipeRefreshLayout)
        })
    }
}