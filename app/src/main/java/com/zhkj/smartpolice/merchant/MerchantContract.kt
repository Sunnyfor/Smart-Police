package com.zhkj.smartpolice.merchant

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView

interface MerchantContract {

    interface IMerchantView : IBaseView {
        fun showMerchantList(data: ArrayList<MerchantBean>)
    }


    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {
        //加载商家列表
        abstract fun loadMerchantList(page: String, shopType: String)
    }
}