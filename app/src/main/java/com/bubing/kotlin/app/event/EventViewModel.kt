package com.bubing.kotlin.app.event

import com.bubing.kotlin.data.model.bean.CollectBus
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.callback.livedata.event.EventLiveData

/**
 * @ClassName: EventViewModel
 * @Author: Bubing
 * @Date: 2020/8/6 3:19 PM
 * @Description: APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 */
class EventViewModel : BaseViewModel() {

    //全局收藏，在任意一个地方收藏或取消收藏，监听该值的界面都会收到消息
    val collectEvent = EventLiveData<CollectBus>()

    //分享文章通知
    val shareArticleEvent = EventLiveData<Boolean>()

    //添加TODO通知
    val todoEvent = EventLiveData<Boolean>()
}