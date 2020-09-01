package com.sunny.zy.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.http.Constant
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.utils.permission.PermissionCode
import java.io.File

/**
 * Desc 相机相册工具类
 */
class CameraUtil(var activity: BaseActivity) {

    var onResultListener: OnResultListener? = null

    private val photoType = 0x81
    private val cameraType = 0x82
    private val cropPictureType = 0x83

    private var messageMap = hashMapOf<String, String>()

    private var file: File? = null
    private var uri: Uri? = null
    private var type = cameraType
    private var missPermission = ""
    private var aspectX = 500
    private var aspectY = 500


    init {
        messageMap[Manifest.permission.CAMERA] = "相机权限"
        messageMap[Manifest.permission.READ_EXTERNAL_STORAGE] = "存储卡读取权限"
        messageMap[Manifest.permission.WRITE_EXTERNAL_STORAGE] = "存储卡写入权限"
    }

    fun startCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            if (checkPermission(permissions)) {
                //有权限
                intentCamera()
            } else {
                //没有权限
                ActivityCompat.requestPermissions(activity, permissions, PermissionCode.CAMERA.type)
            }
        } else {
            intentCamera()
        }
    }


    fun startAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (checkPermission(permissions)) {
                //有权限
                intentPhoto()
            } else {
                //没有权限
                ActivityCompat.requestPermissions(activity, permissions, PermissionCode.STORAGE.type)
            }
        } else {
            intentPhoto()
        }
    }


    //跳转相机
    private fun intentCamera() {
        type = cameraType
        initFile()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)// 更改系统默认存储路径
        activity.startActivityForResult(intent, cameraType)
    }


    //跳转相册
    private fun intentPhoto() {
        type = photoType
        initFile()
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        activity.startActivityForResult(intent, photoType)
    }


    /**
     * 结果回调需要在Activity回调中调用
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                cameraType -> {
                    uri?.let {
                        startPhotoZoom(it)
                    }
                }
                photoType -> {
                    data?.let {
                        startPhotoZoom(it.data ?: return@let)
                    }
                }
                cropPictureType -> {
                    file?.let {
                        onResultListener?.onResult(it)
                    }
                }
            }
        }
    }

    /**
     * 权限回调，需要在Activity权限回调中调用
     */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionCode.CAMERA.type -> {
                if (checkPermission(permissions)) {
                    intentCamera()
                } else {
                    ToastUtil.show("没有${messageMap[missPermission]}权限")
                }
            }

            PermissionCode.STORAGE.type -> {
                if (checkPermission(permissions)) {
                    if (type == cameraType) {
                        intentCamera()
                    } else {
                        intentPhoto()
                    }

                } else {
                    ToastUtil.show("没有${messageMap[missPermission]}权限")
                }
            }

        }
    }

    //裁剪图片
    private fun startPhotoZoom(mUri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        intent.setDataAndType(mUri, "image/*")
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)

        intent.putExtra("aspectX", aspectX)
        intent.putExtra("aspectY", aspectY)
        if (aspectX != 0 && aspectY != 0) {
            intent.putExtra("outputX", aspectX)
            intent.putExtra("outputY", aspectY)
        }
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        activity.startActivityForResult(intent, cropPictureType)
    }


    fun setAspectXY(aspectX: Int, aspectY: Int) {
        this.aspectX = aspectX
        this.aspectY = aspectY
    }


    //裁剪结果回调
    interface OnResultListener {
        fun onResult(file: File)
    }


    //初始化图片文件及URI
    private fun initFile() {
        //有权限
        file = File(UrlConstant.TEMP, "IMG_${System.currentTimeMillis()}.jpg")
        file?.let {
            uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(activity, Constant.authorities, it)
            } else {
                Uri.fromFile(file)
            }
        }

    }

    //检查所有权限是否拥有
    private fun checkPermission(array: Array<String>): Boolean {
        array.forEach {
            if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED) {
                missPermission = it
                return false
            }
        }
        return true
    }
}