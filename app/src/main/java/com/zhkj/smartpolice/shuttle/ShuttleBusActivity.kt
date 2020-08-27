package com.zhkj.smartpolice.shuttle

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.merchant.MerchantBean
import com.zhkj.smartpolice.shuttle.adapter.ShuttleBusAdapter
import com.zhkj.smartpolice.shuttle.model.ShuttleContract
import com.zhkj.smartpolice.shuttle.model.ShuttlePresenter
import kotlinx.coroutines.cancel

class ShuttleBusActivity : BaseActivity(), ShuttleContract.IShuttleBusView {

    private val pullRefreshFragment = PullRefreshFragment<MerchantBean>()

    private val shopId: String by lazy {
        intent.getStringExtra("shopId") ?: ""
    }

    private val adapter: ShuttleBusAdapter by lazy {
        ShuttleBusAdapter()
    }

    private val presenter: ShuttlePresenter by lazy {
        ShuttlePresenter(this)
    }

    companion object {
        fun intent(context: Context, shopId: String?) {
            val intent = Intent(context, ShuttleBusActivity::class.java)
            intent.putExtra("shopId", shopId)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("班车")

        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(getFrameBody().id, pullRefreshFragment).commit()

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        presenter.loadShuttleBusList(pullRefreshFragment.page.toString(), shopId)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun loadShuttleBusList(data: ArrayList<MerchantBean>) {
        pullRefreshFragment.addData(data)
    }

}