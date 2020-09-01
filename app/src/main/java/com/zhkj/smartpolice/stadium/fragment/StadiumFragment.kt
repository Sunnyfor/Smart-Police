package com.zhkj.smartpolice.stadium.fragment

import com.sunny.zy.activity.PullRefreshFragment
import com.zhkj.smartpolice.haircut.bean.ManageBean
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import com.zhkj.smartpolice.stadium.adapter.StadiumResourceAdapter2

class StadiumFragment : PullRefreshFragment<Any>(), MerchantContract.IReserveResourceView {

    var shopType = 0

    private val presenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    init {
        adapter = StadiumResourceAdapter2()
        loadData = {
            //classifyId    1室内   2室外  3澡堂
            presenter.loadReserveResource(page.toString(), "5", "1")
        }
    }


    override fun showReserveResource(data: ArrayList<ManageBean>) {

    }


}