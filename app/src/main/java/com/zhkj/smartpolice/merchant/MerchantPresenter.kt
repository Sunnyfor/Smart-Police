package com.zhkj.smartpolice.merchant

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MerchantPresenter(iBaseView: IBaseView) : MerchantContract.Presenter(iBaseView) {

    private val merchantModel: MerchantModel by lazy {
        MerchantModel()
    }

    override fun loadMerchantList(page: String, shopType: String) {
        launch(Main) {
            showLoading()
            merchantModel.loadMerchantList(page, shopType)?.let {
                if (view is MerchantContract.IMerchantListView) {
                    (view as MerchantContract.IMerchantListView).showMerchantList(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadMerchantInfo(shopId: String) {
        launch(Main) {
            showLoading()
            merchantModel.loadMerchantInfo(shopId)?.data?.let {
                if (view is MerchantContract.IMerchantInfoView) {
                    (view as MerchantContract.IMerchantInfoView).showMerchantInfo(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadMerchantTime(endDate: String, shopId: String) {
        launch(Main) {
            showLoading()
            merchantModel.loadMerchantTime(endDate, shopId)?.let {
                if (view is MerchantContract.IMerchantTimeView) {
                    (view as MerchantContract.IMerchantTimeView).showMerchantTime(it)
                }
            }
            hideLoading()
        }
    }
}