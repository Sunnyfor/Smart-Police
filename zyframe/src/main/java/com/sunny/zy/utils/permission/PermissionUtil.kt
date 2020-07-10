package com.sunny.zy.utils.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseActivity

/**
 * Desc 权限工具类
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020年3月3日 22:25:15
 */
class PermissionUtil(var activity: BaseActivity) {

    private var messageMap = hashMapOf<String, String>()
    private var permissions = kotlin.arrayOf<String>()

    init {
        messageMap[Manifest.permission.CAMERA] = "相机权限"
        messageMap[Manifest.permission.READ_EXTERNAL_STORAGE] = "存储卡读取权限"
        messageMap[Manifest.permission.WRITE_EXTERNAL_STORAGE] = "存储卡写入权限"
    }

    private var missPermission = ""

    /**
     *  检查所有权限是否拥有
     */

    fun checkPermission(array: Array<String>): Boolean {
        array.forEach {
            if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED) {
                missPermission = it
                return false
            }
        }
        return true
    }


    /**
     * 判断是否为6.0以上系统
     */
    private fun isPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true
        }
        return false
    }


    /**
     * 开启相机权限
     * false时需要在Activity权限回调中处理
     */
    fun cameraPermission(): Boolean {
        permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return requestPermission(PermissionCode.CAMERA.type)
    }


    /**
     * 内存卡读写权限
     * false时需要在Activity权限回调中处理
     */
    fun storagePermission(): Boolean {
        permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return requestPermission(PermissionCode.STORAGE.type)
    }


    /**
     * 拨打电话权限
     */
    fun callPermission(): Boolean {
        permissions = arrayOf(
                Manifest.permission.CALL_PHONE)
        return requestPermission(PermissionCode.PHONE.type)
    }


    /**
     * 请求权限
     */
    private fun requestPermission(code: Int): Boolean {
        if (!isPermission()) {
            return true
        }
        if (checkPermission(permissions)) {
            return true
        } else {
            ActivityCompat.requestPermissions(activity, permissions, code)
        }
        return false
    }
}