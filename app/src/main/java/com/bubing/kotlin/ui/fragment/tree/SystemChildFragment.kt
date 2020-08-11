package com.bubing.kotlin.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.*
import com.bubing.kotlin.data.model.bean.CollectBus
import com.bubing.kotlin.databinding.IncludeListBinding
import com.bubing.kotlin.ui.adapter.AriticleAdapter
import com.bubing.kotlin.viewmodel.request.RequestCollectViewModel
import com.bubing.kotlin.viewmodel.request.RequestTreeViewModel
import com.bubing.kotlin.viewmodel.state.TreeViewModel
import com.bubing.kotlin.weight.recyclerview.DefineLoadMoreView
import com.bubing.kotlin.weight.recyclerview.SpaceItemDecoration
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*

/**
 * @ClassName: SystemChildFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:13 PM
 * @Description: 体系栏目fragment
 */
class SystemChildFragment : BaseFragment<TreeViewModel, IncludeListBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    private var cid = -1

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    //适配器
    private val articleAdapter: AriticleAdapter by lazy { AriticleAdapter(arrayListOf()) }

    //收藏viewmodel
    private val requestCollectViewModel: RequestCollectViewModel by viewModels()

    /** 请求ViewModel */
    private val requestTreeViewModel: RequestTreeViewModel by viewModels()

    override fun layoutId() = R.layout.include_list

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            cid = it.getInt("cid")
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestTreeViewModel.getSystemChildData(true, cid)
        }
        //初始化recyclerView
        swipeRecyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            footView = it.initFooter(SwipeRecyclerView.LoadMoreListener {
                requestTreeViewModel.getSystemChildData(false, cid)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatingActionButton)
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            requestTreeViewModel.getSystemChildData(true, cid)
        }
        articleAdapter.run {
            setCollectClick { item, v, position ->
                if (v.isChecked) {
                    requestCollectViewModel.uncollect(item.id)
                } else {
                    requestCollectViewModel.collect(item.id)
                }
            }
            setOnItemClickListener { adapter, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable("ariticleData", articleAdapter.data[position])
                })
            }
            addChildClickViewIds(R.id.item_home_author, R.id.item_project_author)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_home_author, R.id.item_project_author -> {
                        nav().navigateAction(
                            R.id.action_systemArrFragment_to_lookInfoFragment,
                            Bundle().apply {
                                putInt("id", articleAdapter.data[position].userId)
                            })
                    }
                }
            }
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        requestTreeViewModel.getSystemChildData(true, cid)
    }

    override fun createObserver() {
        requestTreeViewModel.systemChildDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadsir, swipeRecyclerView, swipeRefreshLayout)
        })

        requestCollectViewModel.collectUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                //收藏或取消收藏操作成功，发送全局收藏消息
                eventViewModel.collectEvent.postValue(CollectBus(it.id, it.collect))
            } else {
                showMessage(it.errorMsg)
                for (index in articleAdapter.data.indices) {
                    if (articleAdapter.data[index].id == it.id) {
                        articleAdapter.data[index].collect = it.collect
                        articleAdapter.notifyItemChanged(index)
                        break
                    }
                }
            }
        })
        appViewModel.run {
            //监听账户信息是否改变 有值时(登录)将相关的数据设置为已收藏，为空时(退出登录)，将已收藏的数据变为未收藏
            userinfo.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    it.collectIds.forEach { id ->
                        for (item in articleAdapter.data) {
                            if (id.toInt() == item.id) {
                                item.collect = true
                                break
                            }
                        }
                    }
                } else {
                    for (item in articleAdapter.data) {
                        item.collect = false
                    }
                }
                articleAdapter.notifyDataSetChanged()
            })
        }
        //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
        eventViewModel.collectEvent.observe(viewLifecycleOwner, Observer {
            for (index in articleAdapter.data.indices) {
                if (articleAdapter.data[index].id == it.id) {
                    articleAdapter.data[index].collect = it.collect
                    articleAdapter.notifyItemChanged(index)
                    break
                }
            }
        })
    }

    companion object {
        fun newInstance(cid: Int): SystemChildFragment {
            return SystemChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("cid", cid)
                }
            }
        }
    }
}