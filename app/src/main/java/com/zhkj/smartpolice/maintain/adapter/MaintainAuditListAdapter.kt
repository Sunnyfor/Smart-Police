package com.zhkj.smartpolice.maintain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.base.ErrorViewType
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.bean.FindImagePathBean
import com.zhkj.smartpolice.maintain.bean.MaintainAuditBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.item_maintain_audit.view.*


class MaintainAuditListAdapter(info: ArrayList<MaintainAuditBean>, var isType: Boolean) :
    BaseRecycleAdapter<MaintainAuditBean>(info) {

    var findImagePath: ArrayList<FindImagePathBean> = ArrayList()

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(
            R.layout.item_maintain_audit, parent, false
        )

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.tv_apply_date.text = getData(position).applyDate
        holder.itemView.tv_dept_name.text = getData(position).deptName
        holder.itemView.tv_goods.text = getData(position).shopGoodsName
        holder.itemView.tv_style_font_black_small.text = getData(position).applyContent
        LogUtil.i("图片id========${findImagePath}")

//            Glide.with(context)
//                .load(UrlConstant.LOAD_IMAGE_PATH_URL)
//                .dontAnimate()
//                .placeholder(R.drawable.svg_default_image)
//                .into(holder.itemView.iv_maintain_img)

        if (isType) {
            holder.itemView.tv_audit_status.text = "待处理"
        } else {
            holder.itemView.tv_audit_status.text = "待审核"
            holder.itemView.tv_audit_status.setBackgroundResource(R.drawable.sel_maintain_audit_type_background)
        }
    }

}