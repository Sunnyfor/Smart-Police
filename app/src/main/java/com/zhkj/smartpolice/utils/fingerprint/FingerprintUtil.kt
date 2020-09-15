package com.zhkj.smartpolice.utils.fingerprint

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.sunny.zy.utils.ToastUtil
import java.util.*


object FingerprintUtil {

    /**
     * 跳转至指纹设置页
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

    /**
     * 判断是否支持指纹识别
     */
    fun isSupportFingerprint(activity: Activity): Boolean {
        val bpm = BiometricPromptManager.from(activity)
        if (!bpm.isHardwareDetected) {
            ToastUtil.show("您的系统版本过低，不支持指纹功能")
            return false
        } else if (!bpm.isKeyguardSecure) {
            ToastUtil.show("您的手机不支持指纹功能")
            return false
        } else if (!bpm.hasEnrolledFingerprints()) {
            ToastUtil.show("您至少需要在系统设置中添加一个指纹")
            return false
        } else if (bpm.isBiometricPromptEnable) {
            return true
        }
        return false
    }

}