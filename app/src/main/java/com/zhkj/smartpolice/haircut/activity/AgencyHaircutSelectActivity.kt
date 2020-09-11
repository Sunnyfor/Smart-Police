package com.zhkj.smartpolice.haircut.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.haircut.HaircutOrderTimeActivity
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveRecordAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveTimeAdapter
import com.zhkj.smartpolice.haircut.adapter.LeaderReserveWeekAdapter
import com.zhkj.smartpolice.haircut.bean.ManageBean
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean
import com.zhkj.smartpolice.mine.model.ReserveContract
import com.zhkj.smartpolice.mine.model.ReservePresenter
import kotlinx.android.synthetic.main.act_leader_reserve.*
import kotlinx.android.synthetic.main.act_leader_reserve.recycler_time
import kotlinx.android.synthetic.main.act_receive_time.*

/**
 * Desc
 * Author NingYang
 * Mail yang122612@yeah.net
 * Date 2020/9/11 15:09
 */
class AgencyHaircutSelectActivity: HaircutOrderTimeActivity(), MerchantContract.IReserveView, ReserveContract.IReverseRecordView {

    val bean: ManageBean by lazy {
        (intent.getSerializableExtra("bean") as ManageBean)
    }

    private var endDate = ""

    private val recordPresenter: ReservePresenter by lazy {
        ReservePresenter(this)
    }

    private val leaderReserveRecordAdapter = LeaderReserveRecordAdapter()

    override fun setLayout(): Int = R.layout.act_agency_haircut_select

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
    }


    override fun showReverseRecord(data: ArrayList<ReserveRecordBean>) {

    }
}