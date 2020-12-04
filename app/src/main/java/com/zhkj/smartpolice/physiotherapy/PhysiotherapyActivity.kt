package com.zhkj.smartpolice.physiotherapy

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.widget.dialog.PutInSucceedDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveTimeAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveWeekAdapter
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.haircut.bean.WeekDayBean
import com.zhkj.smartpolice.laundry.bean.LaundryLabelBean
import com.zhkj.smartpolice.laundry.view.LaundryView
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import com.zhkj.smartpolice.physiotherapy.presenter.PhysiotherapyPresenter
import com.zhkj.smartpolice.stadium.adapter.StadiumResourceAdapter
import kotlinx.android.synthetic.main.act_physiotherapy.*
import java.util.*
import kotlin.collections.ArrayList

class PhysiotherapyActivity : BaseActivity(), MerchantContract.IReserveTimeView, LaundryView {

    private val defaultWeeks = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    private var weekAdapter: LeaderReserveWeekAdapter? = null
    private var timeAdapter: LeaderReserveTimeAdapter? = null
    private val calendar = Calendar.getInstance(Locale.CHINA)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    private var resourceId: String? = null
    private var endDate = ""
    private var num: Int = 0
    private var casualNum: Int = 0
    private var beginTime: String? = null
    private var endTime: String? = null

    val presenter by lazy {
        MerchantPresenter(this)
    }

    val shopId by lazy {
        intent.getStringExtra("shopId")
    }

    private val putInSucceedDialog: PutInSucceedDialog by lazy {
        PutInSucceedDialog(this)
    }

    private var resourceAdapter = StadiumResourceAdapter().apply {
        setOnItemClickListener { _, position ->
            index = position
            notifyDataSetChanged()
        }
    }

    private val physiotherapyPresenter: PhysiotherapyPresenter by lazy {
        PhysiotherapyPresenter(this)
    }


    override fun setLayout(): Int = R.layout.act_physiotherapy

    override fun initView() {
        defaultTitle("理疗")
        LogUtil.i("获取用用户名密码${UserManager.getUserBean().nickName} ------- ${UserManager.getUserBean().mobile}")
        tv_user_name.text = UserManager.getUserBean().nickName
        tv_user_phone.text = UserManager.getUserBean().mobile

        weekAdapter = LeaderReserveWeekAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                timeAdapter?.clearData()
                timeAdapter?.notifyDataSetChanged()
                resourceAdapter.clearData()
                resourceAdapter.notifyDataSetChanged()
                notifyDataSetChanged()
                endDate = getEndData(getData(position).day)
                LogUtil.d("传递的参数是===endDate===$endDate===shopId====$shopId======resourceId======$resourceId")
                presenter.loadReserveTime(endDate, shopId, resourceId)
            }
        }

        timeAdapter = LeaderReserveTimeAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                notifyDataSetChanged()
                resourceAdapter.clearData()

                list.groupBy { it.manageTime }[getData(position).manageTime]?.let { list ->
                    resourceAdapter.addData(list as java.util.ArrayList<MerchantTime>)
                }
                resourceAdapter.notifyDataSetChanged()
                beginTime = getData(position).beginTime
                endTime = getData(position).endTime
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

        recycler_date.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_date.adapter = weekAdapter

        recycler_time.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_time.adapter = timeAdapter

        recycler_resource.layoutManager = LinearLayoutManager(this)
        recycler_resource.adapter = resourceAdapter

        setOnClickListener(btn_reserve)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_reserve.id -> {
                if (resourceAdapter.itemCount == 0) {
                    ToastUtil.show("当前日期没有可预约的场地！")
                    return
                }

                if (resourceAdapter.index == -1) {
                    ToastUtil.show("请选择预约的场地！")
                    return
                }

                resourceAdapter.getData(resourceAdapter.index).let {
                    physiotherapyPresenter.onPhysiotherapy(
                        it.beginTime ?: "",
                        it.endTime ?: "",
                        "6",
                        it.manageId.toString(),
                        shopId
                    )
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
        return "${calendar.get(Calendar.YEAR)}-${if (month < 10) "0$month" else month}-${if (day < 10) "0$day" else day}"
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
        beginTime = data[0].beginTime
        endTime = data[0].endTime
        resourceAdapter.notifyDataSetChanged()
    }

    override fun showLaundryLabel(data: ArrayList<LaundryLabelBean>) {}

    override fun onLaundryPutIn(succeedBean: BaseModel<Any>) {
        super.onLaundryPutIn(succeedBean)
        putInSucceedDialog.show()
        putInSucceedDialog.onServiceListener = {
            putInSucceedDialog.dismiss()
            ToastUtil.show("预约成功")
            finish()
        }
    }
}