package com.zhkj.smartpolice.laundry.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveTimeAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveWeekAdapter
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.haircut.bean.WeekDayBean
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import com.zhkj.smartpolice.stadium.adapter.StadiumResourceAdapter
import kotlinx.android.synthetic.main.act_laundry_apply.*
import java.util.*

class LaundryApplyActivity : BaseActivity(), MerchantContract.IReserveTimeView {

    private val defaultWeeks = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    private var weekAdapter: LeaderReserveWeekAdapter? = null
    private var timeAdapter: LeaderReserveTimeAdapter? = null
    private val calendar = Calendar.getInstance(Locale.CHINA)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    private var resourceId: String? = null

    private var endDate = ""
    private var num: Int = 0

    val presenter by lazy {
        MerchantPresenter(this)
    }

    val shopId: String by lazy {
        intent.getStringExtra("shopId")
    }

    private var resourceAdapter = StadiumResourceAdapter().apply {
        setOnItemClickListener { _, position ->
            index = position
            notifyDataSetChanged()
        }
    }

    override fun setLayout(): Int = R.layout.act_laundry_apply

    override fun initView() {
        defaultTitle("预约申请")
        iv_add.setOnClickListener(this)
        iv_cut.setOnClickListener(this)
        et_count.setText(num.toString())

        weekAdapter = LeaderReserveWeekAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                timeAdapter?.clearData()
                timeAdapter?.notifyDataSetChanged()
                notifyDataSetChanged()
                endDate = getEndData(getData(position).day)
                presenter.loadReserveTime(endDate, shopId, resourceId)
            }

        }

        timeAdapter = LeaderReserveTimeAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                notifyDataSetChanged()
                resourceAdapter.clearData()

                list.groupBy { it.manageTime }[getData(position).manageTime]?.let { list ->
                    resourceAdapter.addData(list as ArrayList<MerchantTime>)
                }
                resourceAdapter.notifyDataSetChanged()
            }
        }
        val weekDayList = arrayListOf<WeekDayBean>()
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in currentDay..maxDay) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            val week = defaultWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]
            weekDayList.add(WeekDayBean(week, i))
        }
        weekAdapter?.addData(weekDayList)

        rv_date.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_date.adapter = weekAdapter

        recycler_time.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_time.adapter = timeAdapter

//        recycler_resource.layoutManager = LinearLayoutManager(this)
//        recycler_resource.adapter = resourceAdapter

//        setOnClickListener(btn_reserve)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.iv_add -> {
                num += 1
                et_count.setText(num.toString())
            }

            R.id.iv_cut -> {
                if (num > 0) {
                    num -= 1
                    et_count.setText(num.toString())
                }
            }
        }

    }

    override fun loadData() {
        intent.getStringExtra("shopId")?.let {
            presenter.loadReserveTime(getEndData(currentDay), shopId, resourceId)
        }
    }

    override fun close() {
    }

    private fun getEndData(day: Int): String {
        val month = calendar.get(Calendar.MONTH) + 1

        return "${calendar.get(Calendar.YEAR)}-${if (month < 10) "0$month" else month}-$day"
    }

    override fun showReserveTime(data: ArrayList<MerchantTime>) {
        val index = data.indexOf(data.find { it.setNumber - it.reserveNumber > 0 })
        timeAdapter?.index = index
        timeAdapter?.clearData()
        timeAdapter?.addData(data)
        timeAdapter?.notifyDataSetChanged()

        resourceAdapter.clearData()
        data.groupBy { it.manageTime }.let {
            resourceAdapter.addData(it[data[0].manageTime] as java.util.ArrayList<MerchantTime>)
        }

        resourceAdapter.notifyDataSetChanged()
    }

}