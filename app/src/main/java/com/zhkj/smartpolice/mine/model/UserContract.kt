package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.bean.UserBean

interface UserContract {

    interface IImageView : IBaseView {

        fun uploadImage(bean: ImageBean)
    }

    interface IUserInfoView : IBaseView {

        fun loadUserInfo(data: UserBean)

        fun updateUserInfo(msg: String)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //上传图片
        abstract fun uploadImage(url: String, filePath: String)

        //加载用户信息
        abstract fun loadUserInfo()

        //更新用户信息
        abstract fun updateUserInfo(bean: UserBean)
    }
}