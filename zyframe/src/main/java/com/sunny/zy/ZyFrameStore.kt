package com.sunny.zy

import android.content.Context
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.bean.UserInfoBean
import java.util.*

/**
 * 应用类
 * Created by zhangye on 2018/8/2.
 */
object ZyFrameStore {

    private lateinit var instance: Context

    private val activityStack = Stack<BaseActivity>()

    private val userInfoBean = UserInfoBean("", "", "")

    fun init(context: Context) {
        instance = context.applicationContext
    }

    fun getContext() = instance

    private val storeMap = HashMap<String, Any>() //内存数据存储


    fun setUserInfoBean(mUserInfoBean: UserInfoBean) {
        userInfoBean.userId = mUserInfoBean.userId
        userInfoBean.deptId = mUserInfoBean.deptId
        userInfoBean.name = mUserInfoBean.name
    }

    fun getUserInfoBean() = userInfoBean

    @Suppress("UNCHECKED_CAST")
    fun <T> getData(key: String, isDelete: Boolean = false): T? {

        if (!storeMap.containsKey(key)) {
            return null
        }

        val result = storeMap[key]

        if (isDelete) {
            removeData(key)
        }
        return result as T
    }


    /**
     * 存储数据
     */
    fun setData(key: String, t: Any?) {
        if (t != null) {
            storeMap[key] = t
        }
    }

    /**
     * 删除数据
     */
    fun removeData(key: String) {
        storeMap.remove(key)
    }

    /**
     * 存储管理Activity
     */
    fun addActivity(baseActivity: BaseActivity) {
        activityStack.add(baseActivity)
    }

    /**
     * 移除Activity
     */
    fun removeActivity(baseActivity: BaseActivity) {
        activityStack.remove(baseActivity)
    }

    /**
     * 关闭所有的Activity
     */
    fun finishAllActivity() {
        activityStack.forEach {
            it.finish()
        }
        activityStack.clear()
    }
}