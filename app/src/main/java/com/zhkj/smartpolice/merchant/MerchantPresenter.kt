package com.zhkj.smartpolice.merchant

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MerchantPresenter(iBaseView: IBaseView) : MerchantContract.Presenter(iBaseView) {

    private val merchantModel: MerchantModel by lazy {
        MerchantModel()
    }

    override fun loadMerchantList(page: String, shopType: String) {
        launch(Dispatchers.Main) {
            showLoading()
            merchantModel.loadMerchantList(page, shopType)?.let {
                if (view is MerchantContract.IMerchantView) {
                    (view as MerchantContract.IMerchantView).showMerchantList(it)
                }
            }
            hideLoading()
        }
    }
}