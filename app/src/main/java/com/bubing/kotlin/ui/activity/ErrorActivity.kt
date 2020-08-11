package com.bubing.kotlin.ui.activity

import android.content.ClipData
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.ToastUtils
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseActivity
import com.bubing.kotlin.app.ext.init
import com.bubing.kotlin.app.ext.showMessage
import com.bubing.kotlin.util.StatusBarUtil
import com.bubing.kotlin.databinding.ActivityErrorBinding
import com.bubing.kotlin.util.SettingUtil
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.util.clipboardManager
import com.bubing.mvvmk.ext.view.clickNoRepeat
import kotlinx.android.synthetic.main.activity_error.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @ClassName: ErrorActivity
 * @Author: Bubing
 * @Date: 2020/8/6 4:54 PM
 * @Description: 错误展示
 */
class ErrorActivity : BaseActivity<BaseViewModel, ActivityErrorBinding>() {

    override fun layoutId() = R.layout.activity_error

    override fun initView(savedInstanceState: Bundle?)  {
        toolbar.init("发生错误")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))
        StatusBarUtil.setColor(this, SettingUtil.getColor(this), 0)
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        errorRestart.clickNoRepeat{
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        errorSendError.clickNoRepeat {
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessage(it,"发现有Bug不去打作者脸？","必须打",{
                    val mClipData = ClipData.newPlainText("errorLog",it)
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager?.setPrimaryClip(mClipData)
                    ToastUtils.showShort("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=824868922"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtils.showShort("请先安装QQ")
                    }
                },"我不敢")
            }


        }
    }
}