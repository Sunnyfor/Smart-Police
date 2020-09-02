package com.zhkj.smartpolice.stadium.fragment

import com.sunny.zy.activity.PullRefreshFragment
import com.zhkj.smartpolice.haircut.bean.ManageBean
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import com.zhkj.smartpolice.stadium.adapter.StadiumResourceAdapter2

class StadiumFragment : PullRefreshFragment<ManageBean>(), MerchantContract.IReserveResourceView {

    //classifyId    1室内   2室外  3澡堂
    var classifyId = "1"

    private val presenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    override fun initView() {
        adapter = StadiumResourceAdapter2(classifyId)
        loadData = {
            loadData()
        }
        showLoading()

        super.initView()
    }

    override fun loadData() {
        presenter.loadReserveResource(page.toString(), null, "5", classifyId)
    }

    override fun showReserveResource(data: ArrayList<ManageBean>) {
        addData(data)
    }

}