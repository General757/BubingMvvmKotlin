package com.bubing.kotlin.ui.fragment.login
import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bubing.kotlin.R
import com.bubing.kotlin.app.base.BaseFragment
import com.bubing.kotlin.app.ext.hideSoftKeyboard
import com.bubing.kotlin.app.ext.initClose
import com.bubing.kotlin.app.ext.showMessage
import com.bubing.kotlin.databinding.FragmentLoginBinding
import com.bubing.kotlin.util.CacheUtil
import com.bubing.kotlin.util.SettingUtil
import com.bubing.kotlin.viewmodel.request.RequestLoginRegisterViewModel
import com.bubing.kotlin.viewmodel.state.LoginRegisterViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
import com.bubing.mvvmk.ext.parseState
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @ClassName: LoginFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:02 PM
 * @Description: 登录页面
 */
class LoginFragment : BaseFragment<LoginRegisterViewModel, FragmentLoginBinding>() {

    private val requestLoginRegisterViewModel: RequestLoginRegisterViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()


        toolbar.initClose("登录") {
            nav().navigateUp()
        }
        //设置颜色跟主题颜色一致
        appViewModel.appColor.value?.let {
            SettingUtil.setShapColor(loginSub, it)
            loginGoregister.setTextColor(it)
            toolbar.setBackgroundColor(it)
        }
    }

    override fun createObserver() {
        requestLoginRegisterViewModel.loginResult.observe(viewLifecycleOwner,Observer { resultState ->
            parseState(resultState, {
                //登录成功 通知账户数据发生改变鸟
                CacheUtil.setUser(it)
                CacheUtil.setIsLogin(true)
                appViewModel.userinfo.postValue(it)
                nav().navigateUp()
            }, {
                //登录失败
                showMessage(it.errorMsg)
            })
        })
    }

    inner class ProxyClick {

        fun clear() {
            mViewModel.username.value = ""
        }

        fun login() {
            when {
                mViewModel.username.value.isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                else -> requestLoginRegisterViewModel.loginReq(
                    mViewModel.username.value,
                    mViewModel.password.get()
                )
            }
        }

        fun goRegister() {
            hideSoftKeyboard(activity)
            nav().navigateAction(R.id.action_loginFragment_to_registerFrgment)
        }

        var onCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowPwd.set(isChecked)
            }
    }
}