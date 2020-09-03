package com.zhkj.smartpolice.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.*


object FingerprintUtil {

    /**
     * 设置应用角标通知条数
     */
    fun intentSetting(context: Context) {
        Log.d("BRAND", Build.BRAND)

        var pcgName = "com.android.settings"
        var clsName = "com.android.settings.Settings"

        when (Build.BRAND.toLowerCase(Locale.ROOT)) {
            "xiaomi" -> {
                pcgName = "com.android.settings"
                clsName = "com.android.settings.NewFingerprintActivity"
            }
            "huawei", "honor" -> {
                pcgName = "com.android.settings"
                clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity"
            }
            "oppo" -> {
                pcgName = "com.coloros.fingerprint"
                clsName = "com.coloros.fingerprint.FingerprintSettings"
            }
            "vivo" -> {
                //vivo的是没有跳转至指纹设置页面的android.permission.MANAGE_FINGERPRINT权限，该权限是系统应用权限，因此vivo手机只能跳转到设置页面
            }
            "samsung" -> {

            }
            "lenovo" -> {

            }
            "htc" -> {

            }
            "sony" -> {
                pcgName = "com.android.settings"
                clsName = "com.android.settings.Settings.fingerprint.FingerprintEnrollSuggestionActivity"
            }
        }

        val intent = Intent()
        intent.component = ComponentName(pcgName, clsName)
        intent.action = Intent.ACTION_VIEW
        context.startActivity(intent)
    }


}