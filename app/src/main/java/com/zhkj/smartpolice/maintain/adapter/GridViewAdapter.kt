package com.zhkj.smartpolice.maintain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.mine.bean.ImageBean

internal class GridViewAdapter(
    private val context: Context,
    private val list: ArrayList<ImageBean>
) : BaseAdapter() {

    var layoutInflater: LayoutInflater
    private var mImagerView: ImageView? = null

    var onClicklistAdd: (() -> Unit)? = null

    override fun getCount(): Int {
        return list.size + 1
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        convertView = layoutInflater.inflate(R.layout.item_grid, null)
        mImagerView = convertView.findViewById(R.id.iv_item)
        mImagerView?.let {
            if (position < list.size) {
                Glide.with(context)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + list[position].id)
                .dontAnimate()
                .placeholder(R.drawable.svg_default_image)
                .into(it)
//                it.setPadding(0,0,0,0)
            } else {
                it.setImageResource(R.drawable.svg_camera_img)
                it.setPadding(40,40,40,4)
            }

            it.setOnClickListener {
                if (position< list.size) {

                } else {
                    onClicklistAdd?.invoke()
                }
            }
        }
        return convertView
    }

    init {
        layoutInflater = LayoutInflater.from(context)
    }

}