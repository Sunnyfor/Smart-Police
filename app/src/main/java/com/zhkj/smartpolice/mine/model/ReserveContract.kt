package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.mine.bean.RepairRecordBean
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean

interface ReserveContract {

    interface IReverseRecordView : IBaseView {
        fun showReverseRecord(data: ArrayList<ReserveRecordBean>)
    }

    interface IRepairRecordView : IBaseView {
        fun showRepairRecord(data: ArrayList<RepairRecordBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //加载预约记录
        abstract fun loadReverseRecord(page: String, reserveUserId: String? = null, manageId: String? = null)

        //加载预约记录
        abstract fun loadRepairRecord(page: String)

    }
}