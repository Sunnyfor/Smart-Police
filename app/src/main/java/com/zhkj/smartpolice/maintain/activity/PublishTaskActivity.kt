package com.zhkj.smartpolice.maintain.activity

import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.PageModel
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.bean.BarberListBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.act_publish_task.*

class PublishTaskActivity : BaseActivity(), IMaintainView {
    val info = ArrayList<BarberListBean>()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    override fun setLayout(): Int = R.layout.act_publish_task

    override fun initView() {
        defaultTitle("下发 ")
        rv_barber_list.layoutManager = LinearLayoutManager(this)

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        maintainPresenter.onBarberList("46","1","1","4")
    }

    override fun close() {

    }

    override fun onBarberList(barberListBean: ArrayList<BarberListBean>) {
        barberListBean?.let {
            LogUtil.i("维修工人列表=========$barberListBean")
        }
    }
}