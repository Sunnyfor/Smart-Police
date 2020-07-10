package com.sunny.zy.widget.dialog

import android.app.Activity
import android.view.Gravity
import android.view.View
import com.sunny.zy.base.BaseDialog
import kotlinx.android.synthetic.main.layout_dialog_btn.*
import kotlinx.android.synthetic.main.zy_dialog_confirm.*

/**
 * Desc 确认对话框
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2019/10/31 0031 14:53
 */
class ConfirmDialog(activity: Activity) : BaseDialog(activity) {

    var prompt: String? = null

    var cancel: String? = null
    var confirm: String? = null

    /**
     * 文字是否左对齐
     */
    var isLeftAlign: Boolean = false

    /**
     * 取消按钮是否显示
     */
    var isHideCancelBtn: Boolean = false

    override fun show() {
        super.show()

        prompt?.let {
            tv_content.text = it
        }

        if (isLeftAlign) {
            tv_content.gravity = Gravity.CENTER_VERTICAL
        }

        // 取消按钮
        if (isHideCancelBtn) {
            btn_cancel.visibility = View.GONE
        } else {
            btn_cancel.visibility = View.VISIBLE

            cancel?.let {
                btn_cancel.text = it
            }

            btn_cancel.setOnClickListener {
                dismiss()
                onCancelListener?.invoke()
            }
        }

        // 确认按钮
        confirm?.let {
            btn_confirm.text = it
        }

        btn_confirm.setOnClickListener {
            dismiss()
            onConfirmListener?.invoke()
        }

        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }
}