package com.sunny.zy.http

import com.sunny.zy.http.Constant
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorised = originalRequest.newBuilder()
                .header("authorization", getAuthorization())
                .build()
        return chain.proceed(authorised)
    }

    /**
     * 生成服务验证头信息
     */
    private fun getAuthorization(): String {
        val authorizationStr = StringBuilder("sm-auth-${Constant.VERSION}")
        return authorizationStr.toString()
    }
}