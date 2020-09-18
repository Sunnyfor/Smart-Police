package com.zhkj.smartpolice.haircut

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.utils.isPhoneValid
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import kotlinx.android.synthetic.main.act_agency_haircut_select.*
import kotlinx.android.synthetic.main.act_haircut_order_detail.*
import kotlinx.android.synthetic.main.act_haircut_order_detail.tv_hair_color
import kotlinx.android.synthetic.main.act_haircut_order_detail.tv_haircut
import java.util.*

/**
 * 普通警员预约理发
 */
class HaircutOrderDetailActivity : BaseActivity(), MerchantContract.IReserveTimeView, MerchantContract.IReserveView {

    private val calendar = Calendar.getInstance(Locale.CHINA)

    val presenter by lazy {
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
    var haircutType: Int = 1

    override fun setLayout(): Int = R.layout.act_haircut_order_detail

    override fun initView() {

        defaultTitle("预约详情")
        tv_date.text = ("${monthStr}月${dayStr}日 ${defaultWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]}")

        setOnClickListener(cl_date, btn_reserve, tv_haircut, tv_hair_color)

        presenter.loadReserveTime(getEndData(calendar.get(Calendar.DAY_OF_MONTH)), shopId, null)
        tv_user_name.text = UserManager.getUserBean().nickName
        tv_user_phone.text = UserManager.getUserBean().mobile
    }

    override fun onClickEvent(view: View) {
        when (view.id) {

            cl_date.id -> {
                val intent = Intent(this, HaircutOrderTimeActivity::class.java)
                intent.putExtra("shopId", shopId)
                startActivityForResult(intent, 10000)
            }

            btn_reserve.id -> {
                val phone = tv_user_phone.text.toString()
                if (isPhoneValid(phone)) {
                    presenter.commitReserve(tv_user_name.text.toString(), phone, beginTime, endTime, manageId.toString(), "1", shopId,
                        haircutType.toString())
                }
            }

            tv_haircut.id -> {
                tv_haircut.setTextColor(ContextCompat.getColor(this, R.color.font_white))
                tv_haircut.setBackgroundResource(R.drawable.sel_audit_button_border_checked)
                tv_hair_color.setTextColor(ContextCompat.getColor(this, R.color.font_black))
                tv_hair_color.setBackgroundResource(R.drawable.sel_audit_button_border)
                haircutType = 1
            }

            tv_hair_color.id -> {
                tv_hair_color.setTextColor(ContextCompat.getColor(this, R.color.font_white))
                tv_hair_color.setBackgroundResource(R.drawable.sel_audit_button_border_checked)
                tv_haircut.setTextColor(ContextCompat.getColor(this, R.color.font_black))
                tv_haircut.setBackgroundResource(R.drawable.sel_audit_button_border)
                haircutType = 2
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

    private fun getEndData(day: Int): String {
        val month = calendar.get(Calendar.MONTH) + 1

        return "${calendar.get(Calendar.YEAR)}-${if (month < 10) "0$month" else month}-${if (day < 10) "0$day" else day}"
    }

}