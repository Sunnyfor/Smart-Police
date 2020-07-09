package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.IBaseView
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
                    (view as UserContract.IUserInfoView).showUserInfo(it)
                }
            }
        }
    }

}