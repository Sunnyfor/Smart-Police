package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealPresenter(iBaseView: IBaseView) : MealContract.Presenter(iBaseView) {

    private val mealModel: MealModel by lazy {
        MealModel()
    }

    override fun loadRestaurantList(page: String) {
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

    override fun loadMealMenu(shopId: String) {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadMealMenu(shopId)?.let {
                if (view is MealContract.IMealMenuView) {
                    (view as MealContract.IMealMenuView).loadMealMenu(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadMealGoodsList(page: String, shopId: String, labelId: String) {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadMealGoodsList(page, shopId, labelId)?.let {
                if (view is MealContract.IMealMenuView) {
                    (view as MealContract.IMealMenuView).loadMealGoodsList(it)
                }
            }
            hideLoading()
        }
    }

    override fun searchMealGoodsList(shopId: String, searchData: String) {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.searchMealGoodsList(shopId, searchData)?.let {
                if (view is MealContract.IMealMenuView) {
                    (view as MealContract.IMealMenuView).loadMealGoodsList(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadMealRecord(page: String) {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadMealRecord(page)?.let {
                if (view is MealContract.IMealRecordView) {
                    (view as MealContract.IMealRecordView).showMealRecord(it)
                }
            }
            hideLoading()
        }
    }

    override fun commitMealOrder(shopId: String, createUserName: String, mobile: String, totalPrice: String, goodsList: ArrayList<MealGoodsBean>) {

        if (createUserName.isEmpty()){
            view?.showMessage("请填写取餐人姓名")
            return
        }

        if (mobile.isEmpty()){
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