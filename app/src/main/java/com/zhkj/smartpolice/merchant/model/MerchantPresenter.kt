package com.zhkj.smartpolice.merchant.model

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.haircut.bean.ManageBean
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
                if (view is MerchantContract.IReserveInfoView) {
                    (view as MerchantContract.IReserveInfoView).showMerchantInfo(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadReserveTime(endDate: String, shopId: String, resourceId: String?) {
        launch(Main) {
            showLoading()
            merchantModel.loadReserveTime(endDate, shopId, resourceId)?.let {
                if (view is MerchantContract.IReserveTimeView) {
                    (view as MerchantContract.IReserveTimeView).showReserveTime(it)
                }
            }
            hideLoading()
        }
    }

    override fun loadReserveResource(page: String, shopId: String?, shopType: String?, classifyId: String?) {
        launch(Main) {
            merchantModel.loadReserveResource(page, shopId, shopType, classifyId)?.let {
                if (view is MerchantContract.IReserveResourceView) {
                    (view as MerchantContract.IReserveResourceView).showReserveResource(it)
                }
            }
            hideLoading()
        }
    }

    override fun commitReserve(
        reserveUserName: String,
        mobile: String,
        beginTime: String,
        endTime: String,
        manageId: String,
        reserveType: String,
        shopId: String,
        haircutType: String,
        bean: ManageBean?
    ) {

        if (reserveUserName.isEmpty()) {
            view?.showMessage("请填写姓名！")
            return
        }

        if (mobile.isEmpty()) {
            view?.showMessage("请填写手机号码！")
            return
        }

        launch(Main) {
            showLoading()
            merchantModel.commitReserve(reserveUserName, mobile, beginTime, endTime, manageId, reserveType, shopId, haircutType, bean)?.let {
                if (view is MerchantContract.IReserveView) {
                    (view as MerchantContract.IReserveView).reserveResult(it)
                }
            }
            hideLoading()
        }

    }

    override fun commitReserve(
        reserveUserName: String,
        mobile: String,
        beginTime: String,
        endTime: String,
        manageId: String,
        reserveType: String,
        shopId: String,
        bean: ManageBean?
    ) {

        if (reserveUserName.isEmpty()) {
            view?.showMessage("请填写姓名！")
            return
        }

        if (mobile.isEmpty()) {
            view?.showMessage("请填写手机号码！")
            return
        }

        launch(Main) {
            showLoading()
            merchantModel.commitReserve(reserveUserName, mobile, beginTime, endTime, manageId, reserveType, shopId, bean)?.let {
                if (view is MerchantContract.IReserveView) {
                    (view as MerchantContract.IReserveView).reserveResult(it)
                }
            }
            hideLoading()
        }

    }

    override fun commitReserve(
        reserveUserName: String,
        mobile: String,
        beginTime: String,
        endTiem: String,
        manageId: String,
        reserveType: String,
        shopId: String,
        isAgent: String,
        thePrincipalId: String,
        haircutType: String,
        bean: ManageBean?
    ) {
        if (reserveUserName.isEmpty()) {
            view?.showMessage("请填写姓名！")
            return
        }

        if (mobile.isEmpty()) {
            view?.showMessage("请填写手机号码！")
            return
        }

        launch(Main) {
            showLoading()
            merchantModel.commitReserve(
                reserveUserName,
                mobile,
                beginTime,
                endTiem,
                manageId,
                reserveType,
                shopId,
                isAgent,
                thePrincipalId,
                haircutType,
                bean
            )?.let {
                if (view is MerchantContract.IReserveView) {
                    (view as MerchantContract.IReserveView).reserveResult(it)
                }
            }
            hideLoading()
        }
    }


}