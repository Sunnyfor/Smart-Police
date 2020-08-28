package com.zhkj.smartpolice.drugstore.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.drugstore.bean.DrugBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean

interface DrugContract {

    interface IDrugView : IBaseView {
        fun loadDrugClassify(data: ArrayList<MealMenuBean>)
        fun loadDrugList(data: ArrayList<DrugBean>)
    }

    interface IFeedbackView : IBaseView {
        fun commitFeedback(data: String)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //药品分类
        abstract fun loadDrugClassify(shopId: String)

        //药品列表
        abstract fun loadDrugList(page: Int, shopId: String, labelId: String)

        //药品列表搜索
        abstract fun searchDrugList(shopId: String, searchData: String)

        //意见反馈
        abstract fun commitFeedback(shopId: String, content: String, phone: String)
    }
}