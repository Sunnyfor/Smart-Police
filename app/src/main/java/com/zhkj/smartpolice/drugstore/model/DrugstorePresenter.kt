package com.zhkj.smartpolice.drugstore.model

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class DrugstorePresenter(iBaseView: IBaseView) : DrugstoreContract.Presenter(iBaseView) {

    private val model: DrugstoreModel by lazy {
        DrugstoreModel()
    }

    override fun loadDrugClassify(shopId: String) {
        launch(Main) {
            showLoading()
            model.loadDrugClassify(shopId)?.let {
                if (view is DrugstoreContract.IDrugView) {
                    (view as DrugstoreContract.IDrugView).loadDrugClassify(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadDrugList(page: Int, shopId: String, labelId: String) {
        launch(Main) {
            showLoading()
            model.loadDrugList(page, shopId, labelId)?.let {
                if (view is DrugstoreContract.IDrugView) {
                    (view as DrugstoreContract.IDrugView).loadDrugList(it)
                }
            }
            hideLoading()
        }
    }

    override fun commitFeedback(shopId: String, content: String, phone: String) {
        launch(Main) {
            showLoading()
            model.commitFeedback(shopId, content, phone)?.let {
                if (view is DrugstoreContract.IFeedbackView) {
                    (view as DrugstoreContract.IFeedbackView).commitFeedback(it)
                }
            }
            hideLoading()
        }
    }
}