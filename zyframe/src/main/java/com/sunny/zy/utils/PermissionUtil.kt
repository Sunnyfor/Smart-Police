package com.sunny.zy.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseActivity

/**
 * 权限工具类
 * Created by zhangye on 2018/4/16.
 */
class PermissionUtil(private var activity: BaseActivity?) {

    private val firstPermissions = "firstPermissions" //是否是首次授权

    private var messageMap = hashMapOf<String, String>()
    private var permissions = kotlin.arrayOf<String>()

    init {
        messageMap[Manifest.permission.CAMERA] = "相机"
        messageMap[Manifest.permission.WRITE_EXTERNAL_STORAGE] = "文件存储"
        messageMap[Manifest.permission.CALL_PHONE] = "电话"
        messageMap[Manifest.permission.ACCESS_COARSE_LOCATION] = "位置"
    }

    private var missPermissions = ArrayList<String>()

    private var isNotReminding = false  //用户是否勾选不在提醒选项

    /**
     *  检查指定权限是否拥有
     */

    fun checkPermission(array: Array<String>): Boolean {

        var isPermission = true

        missPermissions.clear()
        array.forEach { permission ->
            activity?.let { activity ->
                if (ContextCompat.checkSelfPermission(
                        activity,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    messageMap[permission]?.let {
                        missPermissions.add(it)
                    }
                    isPermission = false

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            activity,
                            permission
                        )
                    ) {
                        isNotReminding = true
                    }
                }
            }
        }
        return isPermission
    }

    /**
     * 判断用户是否勾选了不在提醒
     */
    fun isNotReminding(): Boolean {
        //当用户勾选不在提示
        if (isNotReminding) {
            //判断如果是第一次安装，将勾选不在提示取消
//            if (SharedUtil.getBoolean(firstPermissions, true)) {
//                isNotReminding = false
//                SharedUtil.setBoolean(firstPermissions, false)
//            }
        }
        return isNotReminding
    }


    /**
     * 获取拒绝的权限名字
     */
    fun getMissPerName(): String {
        val nameSb = StringBuilder()
        missPermissions.forEach {
            nameSb.append(it).append("、")
        }
        nameSb.deleteCharAt(nameSb.length - 1)
        return nameSb.toString()
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
            Manifest.permission.CAMERA
        )
        return requestPermission(IntentCode.CAMERA)
    }


    /**
     * 内存卡读写权限
     * false时需要在Activity权限回调中处理
     */
    fun storagePermission(): Boolean {
        permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return requestPermission(IntentCode.STORAGE)
    }


    /**
     * 拨打电话权限
     */
    fun callPermission(): Boolean {
        permissions = arrayOf(
            Manifest.permission.CALL_PHONE
        )
        return requestPermission(IntentCode.PHONE)
    }


    /**
     * 定位权限
     */
    fun locationPermission(): Boolean {
        permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return requestPermission(IntentCode.LOCATION)
    }

    /**
     * 获取所有权限
     */
    fun allPermission(): Boolean {
        permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return requestPermission(IntentCode.ALL)
    }


    /**
     * 开始请求权限
     */
    private fun requestPermission(code: Int): Boolean {
        if (!isPermission()) {
            return true
        }
        if (checkPermission(permissions)) {
            return true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(it, permissions, code)
            }
        }
        return false
    }


    fun onDestroy() {
        activity = null
    }
}