package com.bubing.kotlin.viewmodel.state

import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.callback.databind.StringObservableField

/**
 * @ClassName: LookInfoViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:23 PM
 * @Description: java类作用描述
 */
class LookInfoViewModel : BaseViewModel() {

    var name = StringObservableField("--")

    var imageUrl =
        StringObservableField("https://upload.jianshu.io/users/upload_avatars/9305757/93322613-ff1a-445c-80f9-57f088f7c1b1.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/300/format/webp")

    var info = StringObservableField()
}