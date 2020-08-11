package com.bubing.kotlin.ui.fragment.login
import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.initClose
import com.bubing.kotlin.app.ext.showMessage
import com.bubing.kotlin.databinding.FragmentRegisterBinding
import com.bubing.kotlin.util.CacheUtil
import com.bubing.kotlin.util.SettingUtil
import com.bubing.kotlin.viewmodel.request.RequestLoginRegisterViewModel
import com.bubing.kotlin.viewmodel.state.LoginRegisterViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import com.bubing.mvvmk.ext.parseState
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @ClassName: RegisterFrgment
 * @Author: Bubing
 * @Date: 2020/8/11 3:03 PM
 * @Description: 注册页面
 */
class RegisterFrgment : BaseFragment<LoginRegisterViewModel, FragmentRegisterBinding>() {

    private val requestLoginRegisterViewModel:RequestLoginRegisterViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_register

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        toolbar.initClose("注册") {
            nav().navigateUp()
        }
        //设置颜色跟主题颜色一致
        appViewModel.appColor.value?.let {
            SettingUtil.setShapColor(registerSub, it)
            toolbar.setBackgroundColor(it)
        }
    }

    override fun createObserver() {
        requestLoginRegisterViewModel.loginResult.observe(
            viewLifecycleOwner,
            Observer { resultState ->
                parseState(resultState, {
                    CacheUtil.setIsLogin(true)
                    CacheUtil.setUser(it)
                    appViewModel.userinfo.postValue(it)
                    nav().navigateAction(R.id.action_registerFrgment_to_mainFragment)
                }, {
                    showMessage(it.errorMsg)
                })
            })
    }


    inner class ProxyClick {
        /**清空*/
        fun clear() {
            mViewModel.username.value=""
        }

        /**注册*/
        fun register() {
            when {
                mViewModel.username.value.isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                mViewModel.password2.get().isEmpty() -> showMessage("请填写确认密码")
                mViewModel.password.get().length < 6 -> showMessage("密码最少6位")
                mViewModel.password.get() != mViewModel.password2.get() -> showMessage("密码不一致")
                else -> requestLoginRegisterViewModel.registerAndlogin(
                    mViewModel.username.value,
                    mViewModel.password.get()
                )
            }
        }

        var onCheckedChangeListener1 = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            mViewModel.isShowPwd.set(isChecked)
        }
        var onCheckedChangeListener2 = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            mViewModel.isShowPwd2.set(isChecked)
        }
    }
}