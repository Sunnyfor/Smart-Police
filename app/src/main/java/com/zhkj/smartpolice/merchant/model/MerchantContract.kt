package com.zhkj.smartpolice.merchant.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.haircut.bean.ManageBean
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import com.zhkj.smartpolice.merchant.MerchantBean

interface MerchantContract {

    interface IMerchantListView : IBaseView {
        fun showMerchantList(data: ArrayList<MerchantBean>)
    }

    interface IReserveInfoView : IBaseView {
        fun showMerchantInfo(data: MerchantBean)
    }

    interface IReserveTimeView : IBaseView {
        fun showReserveTime(data: ArrayList<MerchantTime>)
    }

    interface IReserveResourceView : IBaseView {
        fun showReserveResource(data: ArrayList<ManageBean>)
    }

    interface IReserveView : IBaseView {
        fun reserveResult(data: BaseModel<Any>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {
        //加载商家列表
        abstract fun loadMerchantList(page: String, shopType: String)

        //加载商品详情
        abstract fun loadMerchantInfo(shopId: String)

        //加载预约时间
        abstract fun loadReserveTime(endDate: String, shopId: String, resourceId: String?)

        //加载预约资源
        abstract fun loadReserveResource(page: String, shopId: String? = null, shopType: String? = null, classifyId: String? = null)

        //警员理发店预约请求
        abstract fun commitReserve(
            reserveUserName: String,
            mobile: String,
            beginTime: String,
            endTime: String,
            manageId: String,
            reserveType: String,
            shopId: String,
            haircutType: String,
            bean: ManageBean? = null
        )

        //警员预约请求
        abstract fun commitReserve(
            reserveUserName: String,
            mobile: String,
            beginTime: String,
            endTime: String,
            manageId: String,
            reserveType: String,
            shopId: String,
            bean: ManageBean? = null
        )

        //警员司机待预约
        abstract fun commitReserve(
            reserveUserName: String,
            mobile: String,
            beginTime: String,
            endTime: String,
            manageId: String,
            reserveType: String,
            shopId: String,
            isAgent: String,
            thePrincipalId: String,
            haircutType: String,
            bean: ManageBean? = null
        )
    }
}