package com.zhkj.smartpolice.haircut

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.adapter.HaircutTimeAdapter
import com.zhkj.smartpolice.haircut.adapter.HaircutWeekAdapter
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.haircut.bean.WeekDayBean
import com.zhkj.smartpolice.merchant.MerchantContract
import com.zhkj.smartpolice.merchant.MerchantPresenter
import kotlinx.android.synthetic.main.act_haitcut_order_time.*
import java.util.*


class HaircutOrderTimeActivity : BaseActivity(), MerchantContract.IMerchantTimeView {

    private val presenter by lazy {
        MerchantPresenter(this)
    }

    private val shopId: String by lazy {
        intent.getStringExtra("shopId")
    }

    private val calendar = Calendar.getInstance(Locale.CHINA)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    private val defaultWeeks = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")

    val adapter: HaircutTimeAdapter by lazy {
        HaircutTimeAdapter().apply {
            setOnItemClickListener { _, position ->
                if (getData(position).setNumber - getData(position).reserveNumber > 0) {
                    index = position
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun setLayout(): Int = R.layout.act_haitcut_order_time

    override fun initView() {

        defaultTitle("预约时间")

        val weekDayList = arrayListOf<WeekDayBean>()
        val currentWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)


        for (i in currentDay..maxDay) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            val week = defaultWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]
            weekDayList.add(WeekDayBean(week, i))
        }

        recycler_date.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false).apply {

            }

        recycler_date.adapter = HaircutWeekAdapter(currentDay, weekDayList).apply {
            setOnItemClickListener { _: View, position: Int ->
                this.currentDay = getData(position).day
                notifyDataSetChanged()
                presenter.loadMerchantTime(getEndData(this.currentDay), shopId)
            }
        }

        recycler_time.layoutManager = GridLayoutManager(this, 4)
        recycler_time.addItemDecoration(object : RecyclerView.ItemDecoration() {
            private var order_margin = resources.getDimension(R.dimen.dp_8).toInt()
            private var margin = resources.getDimension(R.dimen.dp_4).toInt()

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val position = (parent.getChildLayoutPosition(view) + 1)

                if (position % 4 == 0) {
                    outRect.right = order_margin
                } else {
                    outRect.right = margin
                }


                if ((position - 1) % 4 == 0) {
                    outRect.left = order_margin
                } else {
                    outRect.left = margin
                }

            }
        })
        recycler_time.adapter = adapter

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        presenter.loadMerchantTime(getEndData(currentDay), shopId)
    }

    override fun close() {

    }

    private fun getEndData(day: Int): String {
        val month = calendar.get(Calendar.MONTH) + 1

        return "${calendar.get(Calendar.YEAR)}-${if (month < 10) "0$month" else month}-$day"
    }


    override fun showMerchantTime(data: ArrayList<MerchantTime>) {
        adapter.index = data.indexOf(data.find { it.setNumber - it.reserveNumber > 0 })
        adapter.clearData()
        adapter.addData(data)
        adapter.notifyDataSetChanged()
    }

}