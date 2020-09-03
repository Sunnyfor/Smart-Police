package com.zhkj.smartpolice.laundry.activity

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.widget.dialog.PutInSucceedDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveWeekAdapter
import com.zhkj.smartpolice.haircut.bean.WeekDayBean
import com.zhkj.smartpolice.laundry.adapter.LaundryBtnLabelAdapter
import com.zhkj.smartpolice.laundry.bean.LaundryLabelBean
import com.zhkj.smartpolice.laundry.fragment.LaundrySelectLabelFragment
import com.zhkj.smartpolice.laundry.presenter.LaundryPresenter
import com.zhkj.smartpolice.laundry.view.LaundryView
import kotlinx.android.synthetic.main.act_laundry_apply.*
import java.text.SimpleDateFormat
import java.util.*

class LaundryApplyActivity : BaseActivity(), LaundryView {

    private val defaultWeeks = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    private var weekAdapter: LeaderReserveWeekAdapter? = null
    private val calendar = Calendar.getInstance(Locale.CHINA)


    val shopId: String by lazy {
        intent.getStringExtra("shopId")

    }


    val selfQuota: String by lazy {
        intent.getStringExtra("selfQuota")
    }


    private val laundryPresenter: LaundryPresenter by lazy {
        LaundryPresenter(this)
    }


    var labelType = "2"

    private var labFragmentList = arrayListOf(
        LaundrySelectLabelFragment(),
        LaundrySelectLabelFragment()
    )


    private val putInSucceedDialog: PutInSucceedDialog by lazy {
        PutInSucceedDialog(this)
    }


    override fun setLayout(): Int = R.layout.act_laundry_apply

    override fun initView() {
        defaultTitle("预约申请")
        tv_submit.setOnClickListener(this)

        weekAdapter = LeaderReserveWeekAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                notifyDataSetChanged()
            }
        }

        val weekDayList = arrayListOf<WeekDayBean>()

        for (i in 0 until 7) {
            val week = defaultWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]
            weekDayList.add(WeekDayBean(week, calendar.get(Calendar.DAY_OF_MONTH)).apply {
                date = calendar.time
            })
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)
        }
        weekAdapter?.addData(weekDayList)
        rv_date.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_date.adapter = weekAdapter

        recycler_label.layoutManager = GridLayoutManager(this, 3)
        recycler_label.adapter = LaundryBtnLabelAdapter().apply {
            setOnItemClickListener { _, position ->
                val index = if (labelType == "2") {
                    0
                } else {
                    1
                }
                getData(position).labelName?.let {
                    labFragmentList[index].addData(it)
                }
            }
        }

        radio_group.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.radio_one) {
                labelType = "2"
            }

            if (checkedId == R.id.radio_two) {
                labelType = "3"
            }
            loadData()
        }
        labFragmentList.forEach {
            supportFragmentManager.beginTransaction().add(fl_container.id, it).commit()
        }

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.tv_submit -> {
                val clothesCasualLabel = labFragmentList[0].getLabel()
                val clothesPoliceLabel = labFragmentList[1].getLabel()

                if (clothesCasualLabel == null && clothesPoliceLabel == null) {

                    ToastUtil.show("请选择衣物！")

                    return
                }

                weekAdapter?.index?.let { weekAdapter?.getData(it)?.date }?.let {
                    val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

                    laundryPresenter.saveWashRecord(
                        shopId,
                        null,
                        null,
                        clothesPoliceLabel,
                        clothesCasualLabel,
                        format.format(it)
                    )
                }
            }
        }

    }

    override fun loadData() {
        intent.getStringExtra("shopId")?.let {
            laundryPresenter.loadLaundryLabel(labelType)
        }
    }

    override fun close() {
    }


    override fun showLaundryLabel(data: ArrayList<LaundryLabelBean>) {
        recycler_label.adapter?.let { adapter ->
            if (adapter is LaundryBtnLabelAdapter) {
                adapter.clearData()
                adapter.addData(data)
                adapter.notifyDataSetChanged()
            }
        }

        val fragment = if (labelType == "2") {
            labFragmentList[0]
        } else {
            labFragmentList[1]
        }
        labFragmentList.forEach {
            supportFragmentManager.beginTransaction().hide(it).commit()
        }
        supportFragmentManager.beginTransaction().show(fragment).commit()
    }

    override fun onLaundryPutIn(succeedBean: BaseModel<Any>) {
        putInSucceedDialog.show()
        putInSucceedDialog.onServiceListener = {
            putInSucceedDialog.dismiss()
            ToastUtil.show("预约成功！")
            finish()
        }
    }
}