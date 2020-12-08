package com.sunny.zy.base

import android.app.Dialog
import android.content.Context
import android.view.InflateException
import com.sunny.zy.R

/**
 * Desc 对话框基类
 */
open class BaseDialog(context: Context, layout: Int = R.layout.zy_dialog_confirm) : Dialog(context, R.style.style_common_dialog) {

    var onConfirmListener: (() -> Unit)? = null
    var onCancelListener: (() -> Unit)? = null

    private var isShow = false

    init {
        initView(layout)
    }


    fun initView(layoutRes: Int) {
        try {
            setContentView(layoutRes)
        } catch (e: InflateException) {
            e.printStackTrace()
        }
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun onBackPressed() {
        if (!isShow) {
            super.onBackPressed()
        }
    }

}