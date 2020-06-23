package com.zhkj.smartpolice

import android.app.Application
import com.sunny.zy.ZyFrameStore

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ZyFrameStore.init(this)
    }
}