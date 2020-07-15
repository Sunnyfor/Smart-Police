package com.zhkj.smartpolice.haircut

import android.content.Intent
import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.haircut.adapter.BarberListAdapter
import com.zhkj.smartpolice.haircut.bean.ManageBean
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import kotlinx.coroutines.cancel

/**
 * 理发师列表
 */
class BarberListActivity : BaseActivity(), MerchantContract.IReserveResourceView {

    private val pullRefreshFragment = PullRefreshFragment<ManageBean>()

    private val shopId: String by lazy {
        intent.getStringExtra("shopId") ?: ""
    }

    private val adapter: BarberListAdapter by lazy {
        BarberListAdapter().apply {
            setOnItemClickListener { _, position ->
                val intent = Intent(this@BarberListActivity, LeaderReserveActivity::class.java)
                intent.putExtra("bean", getData(position))
                intent.putExtra("shopId", shopId)
                startActivity(intent)
            }
        }
    }

    private val presenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("理发师列表")


        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(getFrameBody().id, pullRefreshFragment).commit()

        showLoading()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        presenter.loadReserveResource(pullRefreshFragment.page.toString(), shopId)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showReserveResource(data: ArrayList<ManageBean>) {
        pullRefreshFragment.addData(data)
    }


}