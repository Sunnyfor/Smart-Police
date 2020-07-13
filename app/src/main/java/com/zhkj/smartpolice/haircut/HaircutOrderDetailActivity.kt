package com.zhkj.smartpolice.haircut

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_haircut_order_detail.*

/**
 * 普通警员预约理发
 */
class HaircutOrderDetailActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_haircut_order_detail

    override fun initView() {

        defaultTitle("预约详情")
        setOnClickListener(cl_date)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            cl_date.id -> {
                val intent = Intent(this, HaircutOrderTimeActivity::class.java)
                intent.putExtra("shopId",getIntent().getStringExtra("shopId"))
                startActivityForResult(intent, 10000)
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }

}