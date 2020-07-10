package com.zhkj.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.zhkj.wallet.R
import com.zhkj.wallet.adapter.RecordAdapter
import com.zhkj.wallet.bean.RecordBean
import com.zhkj.wallet.contract.WalletContract
import com.zhkj.wallet.presenter.WalletPresenter
import kotlinx.coroutines.cancel

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
        presenter.cancel()
    }

    override fun showRecordData(recordBeans: ArrayList<RecordBean>) {
        hideLoading()
        pullRefreshFragment.addData(recordBeans)
    }
}