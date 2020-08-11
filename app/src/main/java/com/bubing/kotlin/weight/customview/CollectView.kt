package com.bubing.kotlin.weight.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.VibrateUtils
import com.bubing.kotlin.R
import com.bubing.kotlin.util.CacheUtil
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import per.goweii.reveallayout.RevealLayout

/**
 * @ClassName: CollectView
 * @Author: Bubing
 * @Date: 2020/8/7 2:03 PM
 * @Description: java类作用描述
 */
class CollectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RevealLayout(context, attrs, defStyleAttr), View.OnTouchListener {

    private var onCollectViewClickListener: OnCollectViewClickListener? = null

    override fun initAttr(attrs: AttributeSet) {
        super.initAttr(attrs)
        setCheckWithExpand(true)
        setUncheckWithExpand(false)
        setCheckedLayoutId(R.layout.layout_collect_view_checked)
        setUncheckedLayoutId(R.layout.layout_collect_view_unchecked)
        setAnimDuration(300)
        setAllowRevert(true)
        setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                //震动一下
                VibrateUtils.vibrate(40)
                if (CacheUtil.isLogin()) {
                    onCollectViewClickListener?.onClick(this)
                } else {
                    isChecked = true
                    nav(v).navigateAction(R.id.action_to_loginFragment)
                }
            }
        }
        return false
    }

    fun setOnCollectViewClickListener(onCollectViewClickListener: OnCollectViewClickListener) {
        this.onCollectViewClickListener = onCollectViewClickListener
    }

    interface OnCollectViewClickListener {
        fun onClick(v: CollectView)
    }
}