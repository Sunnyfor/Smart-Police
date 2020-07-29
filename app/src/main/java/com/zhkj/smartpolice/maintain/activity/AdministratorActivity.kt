package com.zhkj.smartpolice.maintain.activity

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.adapter.MaintainPhotographAdapter
import com.zhkj.smartpolice.maintain.bean.FindImagePathBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.act_audit_info.*


class
AdministratorActivity : BaseActivity(), IMaintainView {
    var findImagePathBean: ArrayList<FindImagePathBean> = ArrayList()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainPhotographAdapter by lazy {
        MaintainPhotographAdapter(findImagePathBean)
    }

    override fun setLayout(): Int = R.layout.act_audit_info

    override fun initView() {
        defaultTitle("维修详情")
        var intent = intent
        tv_petitioner.text = intent.getStringExtra("petitioner")
        tv_petitioner_phone.text = intent.getStringExtra("petitionerPhone")
        tv_deptName.text = intent.getStringExtra("deptName")
        tv_apply_date.text = intent.getStringExtra("applyDate")
        tv_apply_content.text = intent.getStringExtra("goodsName")
        var groupId = intent.getStringExtra("attachmentGroupId")
        groupId?.let {
            maintainPresenter.onFindImagePath(groupId)
        }
        rv_maintain_img.layoutManager = GridLayoutManager(this, 4)
        rv_maintain_img.adapter = adapter
        tv_refuse.visibility = View.INVISIBLE
        tv_confirm.visibility = View.INVISIBLE
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {

    }

    override fun onFindImagePath(info: ArrayList<FindImagePathBean>) {
        findImagePathBean.clear()
        findImagePathBean.addAll(info)
        adapter.notifyDataSetChanged()
    }
}