package com.sunny.zy.widget.dialog

import android.app.Activity
import android.view.Gravity
import android.view.WindowManager
import com.sunny.zy.R
import com.sunny.zy.base.BaseDialog
import com.sunny.zy.utils.CameraUtil
import kotlinx.android.synthetic.main.zy_dialog_camera.*


/**
 * Desc 相机相册对话框
 */
class CameraDialog(activity: Activity, var cameraUtil: CameraUtil) : BaseDialog(activity, R.layout.zy_dialog_camera) {

    init {
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)
    }

    override fun show() {
        super.show()

        tv_camera.setOnClickListener {
            dismiss()
            cameraUtil.startCamera()
        }
        tv_album.setOnClickListener {
            dismiss()
            cameraUtil.startAlbum()
        }
        tv_cancel.setOnClickListener {
            dismiss()
        }

        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }
}