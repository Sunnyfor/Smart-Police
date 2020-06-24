package com.zhkj.smartpolice

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.sunny.zy.ZyFrameStore

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ZyFrameStore.init(this)
        ARouter.init(this)
        ARouter.openDebug()
        ARouter.openLog()
    }
}