package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.mine.bean.UserBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserPresenter(iBaseView: IBaseView) : UserContract.Presenter(iBaseView) {

    private val model: UserModel by lazy {
        UserModel()
    }

    override fun loadUserInfo() {
        launch(Dispatchers.Main) {
            model.loadUserInfo()?.let {
                if (view is UserContract.IUserInfoView) {
                    (view as UserContract.IUserInfoView).loadUserInfo(it)
                }
            }
        }
    }

    override fun updateUserInfo(bean: UserBean) {
        launch(Dispatchers.Main) {
            showLoading()
            model.updateUserInfo(bean)?.let {
                if (view is UserContract.IUserInfoView) {
                    (view as UserContract.IUserInfoView).updateUserInfo(it)
                }
            }
            hideLoading()
        }
    }
}