package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.meal.bean.MealBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealPresenter(iBaseView: IBaseView) : MealContract.Presenter(iBaseView) {

    private val mealModel: MealModel by lazy {
        MealModel()
    }

    override fun loadRestaurantList(page: Int) {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadRestaurantList(page)?.let {
                if (view is MealContract.IRestaurantView) {
                    (view as MealContract.IRestaurantView).showRestaurantList(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadMealMenu() {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadMealMenu()?.let {
                if (view is MealContract.IMealMenuView) {
                    (view as MealContract.IMealMenuView).loadMealMenu(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadMealList(page: Int, isDine: Boolean, labelId: String) {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadMealList(page, isDine, labelId)?.let {
                if (view is MealContract.IMealMenuView) {
                    (view as MealContract.IMealMenuView).loadMealList(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadMealRecord(page: Int, isConsumeRecord: Boolean?) {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadMealRecord(page, isConsumeRecord)?.let {
                if (view is MealContract.IMealRecordView) {
                    (view as MealContract.IMealRecordView).showMealRecord(it)
                }
            }
            hideLoading()
        }
    }

    override fun commitMealOrder(shopId: String, createUserName: String, mobile: String, totalPrice: String, goodsList: ArrayList<MealBean>) {

        if (createUserName.isEmpty()) {
            view?.showMessage("请填写取餐人姓名")
            return
        }

        if (mobile.isEmpty()) {
            view?.showMessage("请填写你的手机号")
            return
        }

        launch(Dispatchers.Main) {
            showLoading()
            mealModel.commitMealOrder(shopId, createUserName, mobile, totalPrice, goodsList)?.let {
                if (view is MealContract.IMealPlaceAnOrderView) {
                    (view as MealContract.IMealPlaceAnOrderView).showPlaceAnOrderResult(it)
                }
            }
            hideLoading()
        }
    }
}