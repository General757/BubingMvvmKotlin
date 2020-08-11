package com.bubing.kotlin.viewmodel.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.download.DownLoadManager
import com.bubing.mvvmk.ext.download.DownloadResultState
import com.bubing.mvvmk.ext.download.downLoadExt
import kotlinx.coroutines.launch

/**
 * @ClassName: DownloadViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:21 PM
 * @Description: java类作用描述
 */
class DownloadViewModel : BaseViewModel() {

    var downloadData: MutableLiveData<DownloadResultState> = MutableLiveData()

    /**
     * Apk普通下载 框架自带
     * @param path String 文件保存路径
     * @param url String 文件下载url
     * @param tag String 下载标识，根据该值可取消，暂停
     */
    fun downloadApk(path: String, url: String, tag: String) {
        viewModelScope.launch {
            //直接强制下载，不管文件是否存在 ，如果需要每次都重新下载可以设置为true
            DownLoadManager.downLoad(tag, url, path, "tmd.apk", false, downLoadExt(downloadData))
        }
    }

    /**
     * 取消下载
     * @param tag String
     */
    fun downloadCancel(tag: String) {
        DownLoadManager.cancel(tag)
    }

    /**
     * Apk暂停下载
     * @param tag String
     */
    fun downloadPause(tag: String) {
        DownLoadManager.pause(tag)
    }

}