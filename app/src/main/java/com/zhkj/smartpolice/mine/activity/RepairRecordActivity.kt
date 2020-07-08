package com.zhkj.smartpolice.mine.activity

import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.mine.adapter.RepairRecordAdapter
import com.zhkj.smartpolice.mine.bean.RepairRecordBean
import com.zhkj.smartpolice.mine.model.ReserveContract
import com.zhkj.smartpolice.mine.model.ReservePresenter
import kotlinx.coroutines.cancel

/**
 * 维修记录
 */
class RepairRecordActivity : BaseActivity(), ReserveContract.IRepairRecordView {

    private val pullRefreshFragment = PullRefreshFragment<RepairRecordBean>()

    private val adapter = RepairRecordAdapter()

    private val presenter: ReservePresenter by lazy {
        ReservePresenter(this)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("维修记录")

        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(getFrameBody().id, pullRefreshFragment).commit()

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        showLoading()
        presenter.loadRepairRecord(pullRefreshFragment.page.toString())
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showRepairRecord(data: ArrayList<RepairRecordBean>) {
        hideLoading()
        pullRefreshFragment.addData(data)
    }

}