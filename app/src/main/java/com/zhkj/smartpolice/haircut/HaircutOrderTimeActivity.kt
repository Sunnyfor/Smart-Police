package com.zhkj.smartpolice.haircut

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.adapter.HaircutTimeAdapter
import com.zhkj.smartpolice.haircut.adapter.HaircutWeekAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveTimeAdapter
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.haircut.bean.WeekDayBean
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import kotlinx.android.synthetic.main.act_receive_time.*
import java.util.*
import kotlin.collections.ArrayList


open class HaircutOrderTimeActivity : BaseActivity(), MerchantContract.IReserveTimeView, MerchantContract.IReserveView {

    val presenter by lazy {
        MerchantPresenter(this)
    }

    val shopId: String by lazy {
        intent.getStringExtra("shopId")
    }

    var resourceId: String? = null

    private val calendar = Calendar.getInstance(Locale.CHINA)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    private var dataInfo: ArrayList<MerchantTime> = ArrayList();
    var isNumber: Int = 2

    private val defaultWeeks = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")

    var timeAdapter: BaseRecycleAdapter<MerchantTime> = HaircutTimeAdapter().apply {
        setOnItemClickListener { _, position ->
            if (getData(position).setNumber - getData(position).reserveNumber > 0) {
                index = position
                notifyDataSetChanged()
            }
        }
    }

    var weekAdapter: BaseRecycleAdapter<WeekDayBean> = HaircutWeekAdapter(currentDay, arrayListOf()).apply {
        setOnItemClickListener { _: View, position: Int ->
            this.currentDay = getData(position).day
            timeAdapter.clearData()
            timeAdapter.notifyDataSetChanged()
            notifyDataSetChanged()
            presenter.loadReserveTime(getEndData(this.currentDay), shopId, resourceId)
        }
    }

    override fun setLayout(): Int = R.layout.act_receive_time

    override fun initView() {

        defaultTitle("预约时间")

        val weekDayList = arrayListOf<WeekDayBean>()

        LogUtil.i("=====isVerdict=======$isNumber")
        val maxDay = calendar.getActualMaximum(Calendar.DATE)

        for (i in 0 until isNumber) {
            LogUtil.i("i的参数是=======$i")
            val week = defaultWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]
            weekDayList.add(WeekDayBean(week, calendar.get(Calendar.DAY_OF_MONTH)).apply {
                date = calendar.time
            })
            calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + 1)
        }

        weekAdapter.addData(weekDayList)

        recycler_date.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        recycler_date.adapter = weekAdapter

        recycler_time.layoutManager = GridLayoutManager(this, 4)
        recycler_time.adapter = timeAdapter

        setOnClickListener(btn_sure)

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_sure.id -> {
                val intent = Intent()
                (recycler_date.adapter as HaircutWeekAdapter).let {
                    if (timeAdapter is HaircutTimeAdapter) {
                        if (dataInfo != null && dataInfo.size > 0) {
                            intent.putExtra("manageTime", timeAdapter.getData((timeAdapter as HaircutTimeAdapter).index).manageTime)
                        } else {
                            ToastUtil.show("无当前时间段")
                        }
                        if (dataInfo != null && dataInfo.size > 0) {
                            intent.putExtra("day", it.getData(it.index).day)
                            intent.putExtra("week", it.getData(it.index).week)
                        } else {
                            ToastUtil.show("无当前时间段")
                        }
                        if (dataInfo != null && dataInfo.size > 0) {
                            timeAdapter.getData((timeAdapter as HaircutTimeAdapter).index).let { bean ->
                                intent.putExtra("beginTime", bean.beginTime)
                                intent.putExtra("endTime", bean.endTime)

                            }
                        }
                    }
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun loadData() {
        presenter.loadReserveTime(getEndData(currentDay), shopId, resourceId)
    }

    override fun close() {

    }

    fun getEndData(day: Int): String {
        val month = calendar.get(Calendar.MONTH) + 1

        return "${calendar.get(Calendar.YEAR)}-${if (month < 10) "0$month" else month}-${if (day < 10) "0$day" else day}"
    }


    override fun showReserveTime(data: ArrayList<MerchantTime>) {

        val index = data.indexOf(data.find { it.setNumber - it.reserveNumber > 0 })

        if (timeAdapter is HaircutTimeAdapter) {
            (timeAdapter as HaircutTimeAdapter).index = index
        }

        if (timeAdapter is LeaderReserveTimeAdapter) {
            (timeAdapter as LeaderReserveTimeAdapter).index = index
        }
        dataInfo.clear()
        dataInfo.addAll(data)
        timeAdapter.clearData()
        timeAdapter.addData(dataInfo)
        timeAdapter.notifyDataSetChanged()
    }

    override fun reserveResult(data: BaseModel<Any>) {
        ToastUtil.show("预约成功！")
        finish()
    }

}