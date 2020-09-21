package com.zhkj.smartpolice.utils

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils

object BadgeUtils {

    fun setHuaweiBadge(count: Int, context: Context): Boolean {

        return try {
            val launchClassName = getLauncherClassName(context)
            if (TextUtils.isEmpty(launchClassName)) {
                return false
            }
            val bundle = Bundle()
            bundle.putString("package", context.packageName)
            bundle.putString("class", launchClassName)
            bundle.putInt("badgenumber", count)
            context.contentResolver.call(
                Uri.parse("content://com.huawei.android.launcher.settings/badge/"),
                "change_badge", null, bundle
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    private fun getLauncherClassName(context: Context): String {
        val launchComponent = getLauncherComponentName(context)
        return launchComponent?.className ?: ""
    }

    private fun getLauncherComponentName(context: Context): ComponentName? {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(
            context
                .packageName
        )
        return launchIntent?.component
    }
}