package com.zhkj.smartpolice.maintain.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.bean.FindImagePathBean
import com.zhkj.smartpolice.maintain.bean.MaintainAuditBean
import kotlinx.android.synthetic.main.item_maintain_audit.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MaintainAuditListAdapter(info: ArrayList<MaintainAuditBean>, var isType: Boolean) :
    BaseRecycleAdapter<MaintainAuditBean>(info) {

    var findImagePath: ArrayList<FindImagePathBean> = ArrayList()

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(
            R.layout.item_maintain_audit, parent, false
        )

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_apply_date.text = getDate(getData(position).applyDate)
        holder.itemView.tv_dept_name.text = getData(position).deptName
        holder.itemView.tv_goods.text = getData(position).shopGoodsName
        holder.itemView.tv_style_font_black_small.text = getData(position).applyContent
        LogUtil.i("图片id========${findImagePath}")
        getData(position).shopGoodsEntityList?.let {
            Glide.with(context)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it[0].imageId)
                .dontAnimate()
                .placeholder(R.drawable.svg_default_image)
                .into(holder.itemView.iv_maintain_img)
        }

        if (isType) {
            holder.itemView.tv_audit_status.text = "待处理"
        } else {
            holder.itemView.tv_audit_status.text = "待审核"
            holder.itemView.tv_audit_status.setBackgroundResource(R.drawable.sel_maintain_audit_type_background)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(str: String?): String {
        val formatter: java.text.SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return SimpleDateFormat("yyyy-MM-dd").format(formatter.parse(str))
    }

}