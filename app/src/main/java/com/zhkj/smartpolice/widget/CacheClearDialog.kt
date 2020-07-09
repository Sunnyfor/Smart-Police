package com.zhkj.smartpolice.widget

import android.app.Activity
import com.sunny.zy.base.BaseDialog
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.dialog_cache_clear.*


class CacheClearDialog(activity: Activity) : BaseDialog(activity, R.layout.dialog_cache_clear) {

    var cacheSize = "0M"

    init {

        tv_content.text = ("确定要清除缓存吗？")

        btn_confirm.setOnClickListener {
            dismiss()
            onConfirmListener?.invoke()
        }
        btn_cancel.setOnClickListener {
            dismiss()
            onCancelListener?.invoke()
        }

        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }

    override fun show() {
        tv_cache_size.text = ""
        super.show()
        tv_cache_size.text = cacheSize
    }
}