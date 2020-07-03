package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.bean.RestaurantBean

interface MealContract {

    interface IRestaurantView : IBaseView {
        fun showRestaurantList(data: ArrayList<RestaurantBean>)
    }

    interface IMealRecordView : IBaseView {
        fun showMealRecord(data: ArrayList<MealRecordBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        abstract fun loadRestaurantList(page: String) //加载餐厅列表

        abstract fun loadMealRecord(page: String)     //加载订餐记录

    }
}