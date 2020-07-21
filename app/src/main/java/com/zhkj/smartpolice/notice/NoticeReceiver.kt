package com.zhkj.smartpolice.notice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/21 11:23
 */
class NoticeReceiver(var listener: ((hasUnread: Boolean) -> Unit)?) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        intent.action?.let {
            if (it == "com.zhkj.notice.message") {
                listener?.invoke(intent.getBooleanExtra("hasUnread", false))
            }
        }
    }
}