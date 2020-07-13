package com.zhkj.smartpolice.merchant

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.haircut.bean.MerchantTime

interface MerchantContract {

    interface IMerchantListView : IBaseView {
        fun showMerchantList(data: ArrayList<MerchantBean>)
    }

    interface IMerchantInfoView : IBaseView {
        fun showMerchantInfo(data: MerchantBean)
    }

    interface IMerchantTimeView : IBaseView {
        fun showMerchantTime(data: ArrayList<MerchantTime>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {
        //加载商家列表
        abstract fun loadMerchantList(page: String, shopType: String)

        //加载商品详情
        abstract fun loadMerchantInfo(shopId: String)

        //加载预约时间
        abstract fun loadMerchantTime(endDate: String, shopId: String)
    }
}