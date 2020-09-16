package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.meal.bean.MealBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.bean.RestaurantBean

interface MealContract {

    interface IRestaurantView : IBaseView {
        fun showRestaurantList(data: ArrayList<RestaurantBean>)
    }

    interface IMealMenuView : IBaseView {
        fun loadMealMenu(data: ArrayList<MealMenuBean>)
        fun loadMealList(data: ArrayList<MealBean>)
    }

    interface IMealRecordView : IBaseView {
        fun showMealRecord(data: ArrayList<MealRecordBean>)
    }

    interface IMealPlaceAnOrderView : IBaseView {
        fun showPlaceAnOrderResult(data: BaseModel<MealRecordBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //餐厅列表
        abstract fun loadRestaurantList(page: Int)

        //餐厅菜单分类
        abstract fun loadMealMenu()

        //餐厅菜品列表
        abstract fun loadMealList(page: Int, isDine: Boolean, labelId: String)

        //订餐记录
        abstract fun loadMealRecord(page: Int, isConsumeRecord: Boolean? = false)

        //下单
        abstract fun commitMealOrder(shopId: String, createUserName: String, mobile: String, totalPrice: String, goodsList: ArrayList<MealBean>)

    }
}