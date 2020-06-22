package com.sunny.zy.leader

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.PageModel
import com.sunny.zy.bean.AllUsersBean
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/4 11:18
 */
class AllUserPresenter(view: IAllUsersView) : BasePresenter<IAllUsersView>(view) {

    fun getAllUser() {
        launch(Dispatchers.Main) {
            view?.showLoading()
            val params = hashMapOf<String, String>()
            params["page"] = "1"
            params["limit"] = "-1"

            val resultBean = object :HttpResultBean<PageModel<AllUsersBean>>("page"){}
            ZyHttp.post(
                UrlConstant.GET_ALL_USER_LIST_URL,
                params,
                resultBean
            )

            view?.hideLoading()

            if (resultBean.isSuccess()) {
                view?.getAllUser(resultBean.bean?.data?.list ?: arrayListOf())
            } else {
                ToastUtil.show(resultBean.bean?.msg)
            }
        }
    }
}