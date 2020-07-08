package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservePresenter(iBaseView: IBaseView) : ReserveContract.Presenter(iBaseView) {

    private val reverseModel: ReserveModel by lazy {
        ReserveModel()
    }


    override fun loadReverseRecord(page: String) {
        launch(Dispatchers.Main) {
            showLoading()
            reverseModel.loadReverseRecord(page)?.let {
                if (view is ReserveContract.IReverseRecordView) {
                    (view as ReserveContract.IReverseRecordView).showReverseRecord(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadRepairRecord(page: String) {
        launch(Dispatchers.Main) {
            showLoading()
            reverseModel.loadRepairRecord(page)?.let {
                if (view is ReserveContract.IRepairRecordView) {
                    (view as ReserveContract.IRepairRecordView).showRepairRecord(it)
                }
            }
            hideLoading()
        }
    }
}