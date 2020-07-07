package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean

interface ReserveContract {

    interface IReverseRecordView : IBaseView {
        fun showReverseRecord(data: ArrayList<ReserveRecordBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //加载预约记录
        abstract fun loadReverseRecord(page: String)
    }
}