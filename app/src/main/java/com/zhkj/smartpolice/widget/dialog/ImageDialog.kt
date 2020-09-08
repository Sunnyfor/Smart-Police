package com.zhkj.smartpolice.widget.dialog

import android.app.Activity
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import kotlinx.android.synthetic.main.act_image.*


class ImageDialog(var activity: Activity, var id: String?) : BaseDialog(activity, R.layout.act_image) {

    var onImageClickList: (() -> Unit)? = null

    override fun show() {
        super.show()
        Glide.with(activity)
            .load(UrlConstant.LOAD_IMAGE_PATH_URL + id)
            .dontAnimate()
            .placeholder(R.drawable.svg_default_image)
            .into(iv_picture)

        setCancelable(true)

        iv_picture.setOnClickListener {
            onImageClickList?.invoke()
        }
    }
}