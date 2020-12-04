package com.zhkj.smartpolice.mine.activity

import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.mine.adapter.ReverseRecordAdapter
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean
import com.zhkj.smartpolice.mine.model.ReserveContract
import com.zhkj.smartpolice.mine.model.ReservePresenter
import kotlinx.coroutines.cancel

/**
 * 预约记录
 */
class ReserveRecordActivity : BaseActivity(), ReserveContract.IReverseRecordView {

    private val pullRefreshFragment = PullRefreshFragment<ReserveRecordBean>()

    private val adapter = ReverseRecordAdapter()

    private val presenter: ReservePresenter by lazy {
        ReservePresenter(this)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("预约记录")

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
        presenter.loadReverseRecord(pullRefreshFragment.page.toString(), UserManager.getUserBean().userId)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showReverseRecord(data: ArrayList<ReserveRecordBean>) {
        hideLoading()
        pullRefreshFragment.addData(data)
    }

}