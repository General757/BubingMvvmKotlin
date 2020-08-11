package com.bubing.kotlin.viewmodel.state

import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.callback.databind.BooleanObservableField
import com.bubing.mvvmk.callback.databind.StringObservableField
import com.bubing.mvvmk.callback.livedata.StringLiveData

/**
 * @ClassName: LoginRegisterViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:22 PM
 * @Description: 登录注册的ViewModel
 */
class LoginRegisterViewModel : BaseViewModel() {

    //用户名
    var username = StringLiveData()

    //密码(登录注册界面)
    var password = StringObservableField()

    var password2 = StringObservableField()

    //是否显示明文密码（登录注册界面）
    var isShowPwd = BooleanObservableField()

    var isShowPwd2 = BooleanObservableField()

}