package com.bubing.kotlin.data.repository.local

/**
 * @ClassName: LocalDataManger
 * @Author: Bubing
 * @Date: 2020/8/11 2:35 PM
 * @Description: java类作用描述
 */
class LocalDataManger {

    companion object {
        val instance: LocalDataManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LocalDataManger()
        }
    }
}