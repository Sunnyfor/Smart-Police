package com.zhkj.smartpolice.shuttle.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.merchant.MerchantBean

interface ShuttleContract {

    interface IShuttleBusView : IBaseView {
        fun loadShuttleBusList(data: ArrayList<MerchantBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        abstract fun loadShuttleBusList(page: String, shopId: String)
    }
}