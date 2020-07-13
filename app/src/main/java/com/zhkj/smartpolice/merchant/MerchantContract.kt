package com.zhkj.smartpolice.merchant

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView

interface MerchantContract {

    interface IMerchantListView : IBaseView {
        fun showMerchantList(data: ArrayList<MerchantBean>)
    }

    interface IMerchantInfoView : IBaseView {
        fun showMerchantInfo(data:MerchantBean)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {
        //加载商家列表
        abstract fun loadMerchantList(page: String, shopType: String)

        //加载商品详情
        abstract fun loadMerchantInfo(shopId: String)
    }
}