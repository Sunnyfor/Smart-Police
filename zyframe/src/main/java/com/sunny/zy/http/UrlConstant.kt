package com.sunny.zy.http

import com.sunny.zy.ZyFrameStore

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/2 16:47
 */
object UrlConstant {

//    const val host = "https://10.0.0.153:8807" // 内网测试地址

//    const val host = "https://www.zhenhekj.com:8807" //正式地址

    const val IP = "122.51.69.116:8082" //测试环境

//    const val IP = "192.168.1.106:80" //演示接口

    const val host = "http://$IP"

    val TEMP = ZyFrameStore.getContext().getExternalFilesDir("temp")?.path //内存卡缓存路径

}