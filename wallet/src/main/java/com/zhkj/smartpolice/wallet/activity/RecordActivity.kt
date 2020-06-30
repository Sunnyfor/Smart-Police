package com.zhkj.smartpolice.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.wallet.adapter.RecordAdapter
import com.zhkj.smartpolice.wallet.bean.RecordBean
import com.zhkj.smartpolice.wallet.contract.WalletContract
import com.zhkj.smartpolice.wallet.presenter.WalletPresenter

/**
 * 钱包流水页面
 */
@Route(path = RouterManager.RECORD_ACTIVITY)
class RecordActivity : BaseActivity(), WalletContract.IRecordView {

    private val pullRefreshFragment = PullRefreshFragment<RecordBean>()

    private val presenter: WalletPresenter by lazy {
        WalletPresenter(this)
    }

    private val adapter = RecordAdapter()


    override fun setLayout(): Int = 0

    override fun initView() {
        defaultTitle(getString(R.string.record))

        //adapter赋值操作
        pullRefreshFragment.adapter = adapter
        //数据请求事件
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction()
            .replace(getFrameBody().id, pullRefreshFragment).commit()

        showLoading()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        //加载数据
        presenter.loadRecord(pullRefreshFragment.page.toString())
    }

    override fun close() {

    }

    override fun showRecordData(recordBeans: ArrayList<RecordBean>) {
        hideLoading()
        pullRefreshFragment.addData(recordBeans)
    }
}