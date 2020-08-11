package com.bubing.kotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseActivity
import com.bubing.kotlin.databinding.ActivityLaunchBinding
import com.bubing.kotlin.util.CacheUtil
import com.bubing.kotlin.util.SettingUtil
import com.bubing.kotlin.weight.banner.LaunchBannerAdapter
import com.bubing.kotlin.weight.banner.LaunchBannerViewHolder
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.view.gone
import com.bubing.mvvmk.ext.view.visible
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.activity_launch.*

/**
 * @ClassName: LaunchActivity
 * @Author: Bubing
 * @Date: 2020/8/6 2:52 PM
 * @Description: java类作用描述
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class LaunchActivity : BaseActivity<BaseViewModel, ActivityLaunchBinding>() {

    private var resList = arrayOf("说", "学", "逗", "唱", "跳", "r a p")
    private lateinit var mViewPager: BannerViewPager<String, LaunchBannerViewHolder>

    override fun layoutId() = R.layout.activity_launch

    override fun initView(savedInstanceState: Bundle?) {
        //防止出现按Home键回到桌面时，再次点击重新进入该界面bug
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT !== 0) {
            finish()
            return
        }

        mViewDataBind.click = ProxyClick()
        welcome_baseview.setBackgroundColor(SettingUtil.getColor(this))
        mViewPager = findViewById(R.id.banner_view)
        if (CacheUtil.isFirst()) {
            //是第一次打开App 显示引导页
            welcome_image.gone()
            mViewPager.apply {
                adapter = LaunchBannerAdapter()
                setLifecycleRegistry(lifecycle)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if (position == resList.size - 1) {
                            welcomeJoin.visible()
                        } else {
                            welcomeJoin.gone()
                        }
                    }
                })
                create(resList.toList())
            }
        } else {
            //不是第一次打开App 0.3秒后自动跳转到主页
            welcome_image.visible()
            mViewPager.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                //带点渐变动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 300)
        }
    }

    inner class ProxyClick {
        fun toMain() {
            CacheUtil.setFirst(false)
            startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
            finish()
            //带点渐变动画
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

}