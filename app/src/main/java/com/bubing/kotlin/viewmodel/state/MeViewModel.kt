package com.bubing.kotlin.viewmodel.state
import com.bubing.kotlin.util.ColorUtil
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.callback.databind.IntObservableField
import com.bubing.mvvmk.callback.databind.StringObservableField
import com.bubing.mvvmk.callback.livedata.UnPeekLiveData

/**
 * @ClassName: MeViewModel
 * @Author: Bubing
 * @Date: 2020/8/7 5:39 PM
 * @Description: 专门存放 MeFragment 界面数据的ViewModel
 */
class MeViewModel : BaseViewModel() {

    var name = StringObservableField("请先登录~")

    var integral = IntObservableField(0)

    var info = StringObservableField("id：--　排名：-")

    var imageUrl = StringObservableField(ColorUtil.randomImage())

    var testString = UnPeekLiveData<String>()
}