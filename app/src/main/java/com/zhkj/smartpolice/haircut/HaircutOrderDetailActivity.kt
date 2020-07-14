package com.zhkj.smartpolice.haircut

import android.app.Activity
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.merchant.MerchantContract
import com.zhkj.smartpolice.merchant.MerchantPresenter
import kotlinx.android.synthetic.main.act_haircut_order_detail.*
import java.util.*

/**
 * 普通警员预约理发
 */
class HaircutOrderDetailActivity : BaseActivity(), MerchantContract.IReserveTimeView, MerchantContract.IReserveView {

    private val calendar = Calendar.getInstance(Locale.CHINA)

    private val presenter by lazy {
        MerchantPresenter(this)
    }

    private val defaultWeeks = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")

    private val monthStr: String by lazy {
        val month = calendar.get(Calendar.MONTH) + 1
        if (month < 10) "0$month" else "$month"
    }

    private val dayStr: String by lazy {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        if (day < 10) "0$day" else "$day"
    }

    private val shopId: String by lazy {
        intent.getStringExtra("shopId") ?: ""
    }

    private var manageId = 0
    private var beginTime = ""
    private var endTime = ""

    override fun setLayout(): Int = R.layout.act_haircut_order_detail

    override fun initView() {

        defaultTitle("预约详情")
        tv_date.text = ("${monthStr}月${dayStr}日 ${defaultWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]}")

        setOnClickListener(cl_date, btn_reserve)

        presenter.loadReserveTime(
            "${calendar.get(Calendar.YEAR)}-${monthStr}-${calendar.get(Calendar.DAY_OF_MONTH)}", shopId
        )


    }

    override fun onClickEvent(view: View) {
        when (view.id) {

            cl_date.id -> {
                val intent = Intent(this, HaircutOrderTimeActivity::class.java)
                intent.putExtra("shopId", shopId)
                startActivityForResult(intent, 10000)
            }

            btn_reserve.id -> {
                presenter.commitReserve(
                    edit_name.text.toString(),
                    edit_phone.text.toString(),
                    beginTime,
                    endTime,
                    manageId.toString(),
                    "1",
                    shopId
                )
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }

    override fun showReserveTime(data: ArrayList<MerchantTime>) {
        val merchantTime = data.find { it.setNumber - it.reserveNumber > 0 }
        manageId = merchantTime?.manageId ?: 0
        beginTime = merchantTime?.beginTime ?: ""
        endTime = merchantTime?.endTime ?: ""
        tv_time.text = merchantTime?.manageTime
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10000 && resultCode == Activity.RESULT_OK)
            data?.let {
                manageId = it.getIntExtra("manageId", 0)
                beginTime = it.getStringExtra("beginTime") ?: ""
                endTime = it.getStringExtra("endTime") ?: ""
                tv_date.text = ("${monthStr}月${it.getIntExtra("day", 1)}日 ${it.getStringExtra("week")}")
                tv_time.text = it.getStringExtra("manageTime")
            }
    }


    override fun reserveResult(data: BaseModel<Any>) {
        ToastUtil.show("预约成功！")
        finish()
    }
}