package com.zhkj.smartpolice.utils.dict

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.sunny.zy.bean.DictBean
import com.zhkj.smartpolice.utils.dict.bean.DeptBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Desc  工具类
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020年10月23日 16:46:27
 */
interface DictContract {

    interface IDictView : IBaseView {
        fun loadDictList(data: ArrayList<DictBean>)
    }

    interface IDeptView : IBaseView {
        fun loadDeptList(data: ArrayList<DeptBean>)
    }

    class Presenter(view: IBaseView) : BasePresenter<IBaseView>(view) {

        val sex = "sex"
        val position = "position"
        val userType = "userType"

        private val dictModel: DictModel by lazy {
            DictModel()
        }

        suspend fun getDictBean(code: String, type: String): String {
            return dictModel.getDictBean(code, type)
        }

        fun loadDictList(type: String) {
            launch(Dispatchers.Main) {
                dictModel.findDiceList(type)?.let {
                    if (view is IDictView)
                        (view as IDictView).loadDictList(it)
                }
            }
        }


        fun loadDeptList() {
            launch(Dispatchers.Main) {
                dictModel.loadDeptList()?.let {
                    if (view is IDeptView)
                        (view as IDeptView).loadDeptList(it)
                }
            }
        }


    }
}