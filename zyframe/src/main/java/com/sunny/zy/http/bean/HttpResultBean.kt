package com.sunny.zy.http.bean

import com.sunny.zy.ZyFrameStore
import com.sunny.zy.utils.ToastUtil
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

abstract class HttpResultBean<T>(
    var serializedName: String = "data"
) : BaseHttpResultBean<T>() {

    var msg: String? = "OK"  //请求结果
    var bean: T? = null //数据结果

    @Suppress("UNCHECKED_CAST")
    fun isSuccess(): Boolean {
        if (httpIsSuccess()) {
            return if (ZyFrameStore.onSuccessCallback != null) {
                ZyFrameStore.onSuccessCallback?.invoke(this as BaseHttpResultBean<Any>) == true
            } else {
                true
            }
        }
        ToastUtil.show(msg)
        return false
    }

    override fun notifyData(baseHttpResultBean: BaseHttpResultBean<T>) {}

    override fun onOpen(webSocket: WebSocket, response: Response) {}

    override fun onMessage(webSocket: WebSocket, text: String) {}

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {}

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {}

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {}

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {}


    override fun toString(): String {
        return "HttpResultBean(httpCode=$httpCode, msg='$msg', exception=$exception, bean=$bean)"
    }


}