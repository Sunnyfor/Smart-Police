package com.zhkj.smartpolice.drugstore.model

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class DrugPresenter(iBaseView: IBaseView) : DrugContract.Presenter(iBaseView) {

    private val model: DrugModel by lazy {
        DrugModel()
    }

    override fun loadDrugClassify(shopId: String) {
        launch(Main) {
            showLoading()
            model.loadDrugClassify(shopId)?.let {
                if (view is DrugContract.IDrugView) {
                    (view as DrugContract.IDrugView).loadDrugClassify(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadDrugList(page: Int, shopId: String, labelId: String) {
        launch(Main) {
            showLoading()
            model.loadDrugList(page, shopId, labelId)?.let {
                if (view is DrugContract.IDrugView) {
                    (view as DrugContract.IDrugView).loadDrugList(it)
                }
            }
            hideLoading()
        }
    }


    override fun searchDrugList(shopId: String, searchData: String) {
        launch(Main) {
            showLoading()
            model.searchDrugList(shopId, searchData)?.let {
                if (view is DrugContract.IDrugView) {
                    (view as DrugContract.IDrugView).loadDrugList(it)
                }
            }
            hideLoading()
        }
    }

    override fun commitFeedback(shopId: String, content: String, phone: String) {
        launch(Main) {
            showLoading()
            model.commitFeedback(shopId, content, phone)?.let {
                if (view is DrugContract.IFeedbackView) {
                    (view as DrugContract.IFeedbackView).commitFeedback(it)
                }
            }
            hideLoading()
        }
    }
}