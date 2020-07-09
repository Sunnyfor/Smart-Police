package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.mine.bean.UserBean

interface UserContract {

    interface IUserInfoView : IBaseView {
        fun showUserInfo(data: UserBean)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //加载用户信息
        abstract fun loadUserInfo()

    }
}