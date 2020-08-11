package com.bubing.kotlin.viewmodel.state

import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.callback.databind.StringObservableField

/**
 * @ClassName: AriticleViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:19 PM
 * @Description: java类作用描述
 */
class AriticleViewModel : BaseViewModel() {

    //分享文章标题
    var shareTitle = StringObservableField()

    //分享文章网址
    var shareUrl = StringObservableField()

    //分享文章的人
    var shareName = StringObservableField()

}