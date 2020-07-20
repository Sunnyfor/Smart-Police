package com.zhkj.smartpolice.laundry.activity

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveTimeAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveWeekAdapter
import com.zhkj.smartpolice.haircut.bean.WeekDayBean
import kotlinx.android.synthetic.main.act_laundry_apply.*
import java.util.*

class LaundryApplyActivity : BaseActivity() {

    private val defaultWeeks = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    private var weekAdapter: LeaderReserveWeekAdapter? = null
    private var timeAdapter: LeaderReserveTimeAdapter? = null
    private val calendar = Calendar.getInstance(Locale.CHINA)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    private var endDate = ""
    override fun setLayout(): Int = R.layout.act_laundry_apply

    override fun initView() {
        defaultTitle("预约申请")
        weekAdapter = LeaderReserveWeekAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                timeAdapter?.clearData()
                timeAdapter?.notifyDataSetChanged()

//                resourceAdapter.clearData()
//                resourceAdapter.notifyDataSetChanged()
                notifyDataSetChanged()
                endDate = getEndData(getData(position).day)
//                presenter.loadReserveTime(endDate, shopId, resourceId)
                ToastUtil
            }

        }

        timeAdapter = LeaderReserveTimeAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                notifyDataSetChanged()

//                resourceAdapter.clearData()

                list.groupBy { it.manageTime }[getData(position).manageTime]?.let { list ->
//                    resourceAdapter.addData(list as ArrayList<MerchantTime>)
                }
//                resourceAdapter.notifyDataSetChanged()
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
        rv_date.addItemDecoration(ItemDecoration(true))
        recycler_time.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_time.adapter = timeAdapter
        recycler_time.addItemDecoration(ItemDecoration(false))

//        recycler_resource.layoutManager = LinearLayoutManager(this)
//        recycler_resource.adapter = resourceAdapter

    }

    override fun onClickEvent(view: View) {
    }

    override fun loadData() {
        intent.getStringExtra("shopId")?.let {
//            presenter.loadReserveTime(getEndData(currentDay), shopId, resourceId)
        }
    }

    private fun getEndData(day: Int): String {
        val month = calendar.get(Calendar.MONTH) + 1

        return "${calendar.get(Calendar.YEAR)}-${if (month < 10) "0$month" else month}-$day"
    }

    override fun close() {
    }

    inner class ItemDecoration(var boolean: Boolean) : RecyclerView.ItemDecoration() {
        private var borderMargin = resources.getDimension(R.dimen.dp_18).toInt()
        private var margin = resources.getDimension(R.dimen.dp_9).toInt()

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent.getChildAdapterPosition(view)
            if (boolean) {
                view.layoutParams.width = resources.getDimension(R.dimen.dp_44).toInt()
            }

            if (position == (parent.adapter?.itemCount ?: 0 - 1)) {
                outRect.right = borderMargin
            } else {
                outRect.right = margin
            }


            if (position == 0) {
                outRect.left = borderMargin
            } else {
                outRect.left = margin
            }
        }
    }
}