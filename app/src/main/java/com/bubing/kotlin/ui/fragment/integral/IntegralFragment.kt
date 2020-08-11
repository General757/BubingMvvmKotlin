package com.bubing.kotlin.ui.fragment.integral

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.*
import com.bubing.kotlin.data.model.bean.BannerResponse
import com.bubing.kotlin.data.model.bean.IntegralResponse
import com.bubing.kotlin.databinding.FragmentIntegralBinding
import com.bubing.kotlin.ui.adapter.IntegralAdapter
import com.bubing.kotlin.viewmodel.request.RequestIntegralViewModel
import com.bubing.kotlin.viewmodel.state.IntegralViewModel
import com.bubing.kotlin.weight.recyclerview.SpaceItemDecoration
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import com.bubing.mvvmk.ext.util.notNull
import com.bubing.mvvmk.ext.view.gone
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_integral.*
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @ClassName: IntegralFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:01 PM
 * @Description: 积分排行
 */
class IntegralFragment : BaseFragment<IntegralViewModel, FragmentIntegralBinding>() {
    private var rank: IntegralResponse? = null

    //适配器
    private lateinit var integralAdapter: IntegralAdapter

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //请求的ViewModel /** */
    private val requestIntegralViewModel: RequestIntegralViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_integral

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        rank = arguments?.getParcelable("rank")
        rank.notNull({
            mViewModel.rank.set(rank)
        }, {
            integral_cardview.gone()
        })
        integralAdapter = IntegralAdapter(arrayListOf(), rank?.rank ?: -1)
        toolbar.run {
            inflateMenu(R.menu.menu_integral)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.integral_guize -> {
                        nav().navigateAction(R.id.action_to_webFragment,
                            Bundle().apply {
                                putParcelable(
                                    "bannerdata",
                                    BannerResponse(
                                        title = "积分规则",
                                        url = "https://www.wanandroid.com/blog/show/2653"
                                    )
                                )
                            }
                        )
                    }
                    R.id.integral_history -> {
                        nav().navigateAction(R.id.action_integralFragment_to_integralHistoryFragment)
                    }
                }
                true
            }
            initClose("积分排行") {
                nav().navigateUp()
            }
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestIntegralViewModel.getIntegralData(true)
        }
        //初始化recyclerView
        swipeRecyclerView.init(LinearLayoutManager(context), integralAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFooter(SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                requestIntegralViewModel.getIntegralData(false)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatingActionButton)
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            requestIntegralViewModel.getIntegralData(true)
        }
        appViewModel.appColor.value?.let {
            setUiTheme(
                it,
                integral_merank, integral_mename, integral_mecount
            )
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestIntegralViewModel.getIntegralData(true)
    }

    override fun createObserver() {
        requestIntegralViewModel.integralDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, integralAdapter, loadsir, swipeRecyclerView, swipeRefreshLayout)
        })
    }
}