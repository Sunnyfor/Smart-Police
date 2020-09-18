package com.zhkj.smartpolice.haircut

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.base.BaseModel
import com.sunny.zy.utils.GlideApp
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveRecordAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveTimeAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveWeekAdapter
import com.zhkj.smartpolice.haircut.bean.ManageBean
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean
import com.zhkj.smartpolice.mine.model.ReserveContract
import com.zhkj.smartpolice.mine.model.ReservePresenter
import kotlinx.android.synthetic.main.act_haircut_order_detail.*
import kotlinx.android.synthetic.main.act_leader_reserve.*
import kotlinx.android.synthetic.main.act_leader_reserve.btn_sure
import kotlinx.android.synthetic.main.act_leader_reserve.tv_hair_color
import kotlinx.android.synthetic.main.act_leader_reserve.tv_haircut
import kotlinx.android.synthetic.main.act_leader_reserve.tv_name
import kotlinx.android.synthetic.main.act_receive_time.recycler_time

class LeaderReserveActivity : HaircutOrderTimeActivity(), MerchantContract.IReserveView, ReserveContract.IReverseRecordView {

    val bean: ManageBean by lazy {
        (intent.getSerializableExtra("bean") as ManageBean)
    }

    private var endDate = ""
    var haircutType: Int = 1

    private val recordPresenter: ReservePresenter by lazy {
        ReservePresenter(this)
    }

    private val leaderReserveRecordAdapter = LeaderReserveRecordAdapter()


    override fun setLayout(): Int = R.layout.act_leader_reserve

    override fun initView() {
        resourceId = bean.resourceId
        isNumber = 3
        weekAdapter = LeaderReserveWeekAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position
                timeAdapter.clearData()
                timeAdapter.notifyDataSetChanged()

                leaderReserveRecordAdapter.clearData()
                leaderReserveRecordAdapter.notifyDataSetChanged()

                notifyDataSetChanged()
                endDate = getEndData(getData(position).day)
                presenter.loadReserveTime(endDate, shopId, resourceId)
            }

        }
        timeAdapter = LeaderReserveTimeAdapter().apply {
            setOnItemClickListener { _, position ->
                index = position

                notifyDataSetChanged()

            }
        }

        tv_name.text = bean.resourceName
        tv_desc.text = bean.resourceContext

        GlideApp.with(this).load(UrlConstant.LOAD_IMAGE_PATH_URL + bean.imageId)
            .placeholder(R.drawable.svg_default_head)
            .into(iv_head)

        super.initView()

        recycler_time.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        recycler_record.layoutManager = LinearLayoutManager(this)
        recycler_record.adapter = leaderReserveRecordAdapter
        setOnClickListener(btn_sure, tv_haircut, tv_hair_color)

    }


    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_sure.id -> {
                (timeAdapter as LeaderReserveTimeAdapter).let {
                    presenter.commitReserve(
                        UserManager.getUserBean().nickName ?: "",
                        UserManager.getUserBean().mobile ?: "",
                        it.getData(it.index).beginTime ?: "",
                        it.getData(it.index).endTime ?: "",
                        it.getData(it.index).manageId.toString(),
                        "1",
                        shopId,
                        haircutType.toString(),
                        bean
                    )
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


    override fun showReserveTime(data: ArrayList<MerchantTime>) {
        super.showReserveTime(data)
        val manageId = data[(timeAdapter as LeaderReserveTimeAdapter).index].manageId
        recordPresenter.loadReverseRecord("1", "", manageId.toString())
    }

    override fun reserveResult(data: BaseModel<Any>) {
        ToastUtil.show("预约成功！")
        finish()
    }

    override fun showReverseRecord(data: ArrayList<ReserveRecordBean>) {
        leaderReserveRecordAdapter.clearData()
        leaderReserveRecordAdapter.addData(data)
        leaderReserveRecordAdapter.notifyDataSetChanged()
    }
}