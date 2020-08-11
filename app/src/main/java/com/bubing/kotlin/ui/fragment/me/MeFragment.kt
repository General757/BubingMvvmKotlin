package com.bubing.kotlin.ui.fragment.me

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_me.*
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.init
import com.bubing.kotlin.app.ext.joinQQGroup
import com.bubing.kotlin.app.ext.jumpByLogin
import com.bubing.kotlin.app.ext.setUiTheme
import com.bubing.kotlin.data.model.bean.BannerResponse
import com.bubing.kotlin.data.model.bean.IntegralResponse
import com.bubing.kotlin.databinding.FragmentMeBinding
import com.bubing.kotlin.viewmodel.request.RequestMeViewModel
import com.bubing.kotlin.viewmodel.state.MeViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import com.bubing.mvvmk.ext.parseState
import com.bubing.mvvmk.ext.util.notNull

/**
 * @ClassName: MeFragment
 * @Author: Bubing
 * @Date: 2020/8/6 5:10 PM
 * @Description: 我的
 */
class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {

    private var rank: IntegralResponse? = null

    private val requestMeViewModel: RequestMeViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_me

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
        appViewModel.appColor.value?.let { setUiTheme(it, me_linear, me_integral) }
        appViewModel.userinfo.value?.let { mViewModel.name.set(if (it.nickname.isEmpty()) it.username else it.nickname) }
        me_swipe.init {
            requestMeViewModel.getIntegral()
        }
    }

    override fun lazyLoadData() {
        appViewModel.userinfo.value?.run {
            me_swipe.isRefreshing = true
            requestMeViewModel.getIntegral()
        }
    }

    override fun createObserver() {
        requestMeViewModel.meData.observe(viewLifecycleOwner, Observer { resultState ->
            me_swipe.isRefreshing = false
            parseState(resultState, {
                rank = it
                mViewModel.info.set("id：${it.userId}　排名：${it.rank}")
                mViewModel.integral.set(it.coinCount)
            }, {
                ToastUtils.showShort(it.errorMsg)
            })
        })

        appViewModel.run {
            appColor.observe(viewLifecycleOwner, Observer {
                setUiTheme(it, me_linear, me_swipe, me_integral)
            })
            userinfo.observe(viewLifecycleOwner, Observer {
                it.notNull({
                    me_swipe.isRefreshing = true
                    mViewModel.name.set(if (it.nickname.isEmpty()) it.username else it.nickname)
                    requestMeViewModel.getIntegral()
                }, {
                    mViewModel.name.set("请先登录~")
                    mViewModel.info.set("id：--　排名：--")
                    mViewModel.integral.set(0)
                })
            })
        }
    }

    inner class ProxyClick {

        /** 登录 */
        fun login() {
            nav().jumpByLogin {}
        }

        /** 收藏 */
        fun collect() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_collectFragment)
            }
        }

        /** 积分 */
        fun integral() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_integralFragment,
                    Bundle().apply {
                        putParcelable("rank", rank)
                    }
                )
            }
        }

        /** 文章 */
        fun ariticle() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_ariticleFragment)
            }
        }

        /** Todo */
        fun todo() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_todoListFragment)
            }
        }

        /** 玩Android开源网站 */
        fun about() {
            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                putParcelable(
                    "bannerdata",
                    BannerResponse(
                        title = "玩Android网站",
                        url = "https://www.wanandroid.com/"
                    )
                )
            })
        }

        /** 加入我们 */
        fun join() {
            joinQQGroup("9n4i5sHt4189d4DvbotKiCHy-5jZtD4D")
        }

        /** 设置*/
        fun setting() {
            nav().navigateAction(R.id.action_mainfragment_to_settingFragment)
        }

        /**demo*/
        fun demo() {
            nav().navigateAction(R.id.action_mainfragment_to_demoFragment)
        }
    }
}