package com.sunny.zy.widget.dialog

import android.app.Activity
import com.sunny.zy.R
import com.sunny.zy.base.BaseDialog
import kotlinx.android.synthetic.main.zy_dialog_succeed.*


class PutInSucceedDialog(activity: Activity): BaseDialog(activity, R.layout.zy_dialog_succeed) {

    var onServiceListener: (() -> Unit)? = null

    override fun show() {
        super.show()
        tv_succeed.setOnClickListener{
            onServiceListener?.invoke()
        }
    }
}