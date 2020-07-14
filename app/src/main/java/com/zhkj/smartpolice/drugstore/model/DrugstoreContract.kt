package com.zhkj.smartpolice.drugstore.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView

interface DrugstoreContract {

    interface IFeedbackView : IBaseView {
        fun commitFeedback(data: String)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //意见反馈
        abstract fun commitFeedback(shopId: String, content: String, phone: String)
    }
}