package com.bubing.mvvmk.network.manager

import com.bubing.mvvmk.callback.livedata.UnPeekLiveData

/**
 * @ClassName: NetworkStateManager
 * @Author: Bubing
 * @Date: 2020/8/6 10:58 AM
 * @Description: 网络变化管理者
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}