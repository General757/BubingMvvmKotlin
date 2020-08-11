package com.bubing.mvvmk.ext.download

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @ClassName: DownLoadService
 * @Author: Bubing
 * @Date: 2020/8/10 3:14 PM
 * @Description: java类作用描述
 */
interface DownLoadService {
    @Streaming
    @GET
    suspend fun downloadFile(
        @Header("RANGE") start: String,
        @Url url: String
    ): Response<ResponseBody>
}