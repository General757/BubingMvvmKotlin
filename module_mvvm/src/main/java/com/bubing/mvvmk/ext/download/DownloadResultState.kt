package com.bubing.mvvmk.ext.download

/**
 * @ClassName: DownloadResultState
 * @Author: Bubing
 * @Date: 2020/8/10 3:13 PM
 * @Description: java类作用描述
 */
sealed class DownloadResultState {

    companion object {

        fun onPending(): DownloadResultState = Pending

        fun onProgress(soFarBytes: Long, totalBytes: Long, progress: Int): DownloadResultState =
            Progress(soFarBytes, totalBytes, progress)

        fun onSuccess(filePath: String, totalBytes: Long): DownloadResultState =
            Success(filePath, totalBytes)

        fun onPause(): DownloadResultState = Pause

        fun onError(errorMsg: String): DownloadResultState = Error(errorMsg)
    }

    object Pending : DownloadResultState()
    data class Progress(val soFarBytes: Long, val totalBytes: Long, val progress: Int) :
        DownloadResultState()

    data class Success(val filePath: String, val totalBytes: Long) : DownloadResultState()
    object Pause : DownloadResultState()
    data class Error(val errorMsg: String) : DownloadResultState()
}