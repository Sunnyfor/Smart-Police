package com.sunny.zy.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider

import com.sunny.zy.R
import com.sunny.zy.base.BaseActivity
import java.io.File


/**
 * 相机相册工具类
 * Created by zhangye on 2018/4/10.
 */
class CameraPhotoUtils(
    private var activity: BaseActivity?,
    private var onResultListener: OnResultListener?
) {
    private var messageMap = hashMapOf<String, String>()
    private var permissionUtil: PermissionUtil? = null
    private var file: File? = null
    private var uri: Uri? = null
    private var type = IntentCode.CAMERA
    private var missPermission = ""
//    private var aspectX: Int
//    private var aspectY: Int

    init {
        permissionUtil = PermissionUtil(activity)
//        aspectX = activity?.resources?.getDimension(R.dimen.w318)?.toInt() ?: 0
//        aspectY = activity?.resources?.getDimension(R.dimen.w318)?.toInt() ?: 0
    }


    fun startCamera() {
        permissionUtil?.let {
            if (it.cameraPermission()) {
                intentCamera()
            }
        }
    }


    fun startPhoto() {
        permissionUtil?.let {
            if (it.storagePermission()) {
                intentPhoto()
            }
        }
    }


    //跳转相机
    private fun intentCamera() {
        type = IntentCode.CAMERA
        initFile()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)// 更改系统默认存储路径
        activity?.startActivityForResult(intent, type)
    }


    //跳转相册
    private fun intentPhoto() {
        type = IntentCode.PHOTO
        initFile()
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        activity?.startActivityForResult(intent, type)
    }


    /**
     * 结果回调需要在Activity回调中调用
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IntentCode.CAMERA -> {
                    uri?.let {
                        startPhotoZoom(it)
                    }
                }
                IntentCode.PHOTO -> {
                    data?.let {
                        startPhotoZoom(it.data ?: return)
                    }
                }

                IntentCode.CROP -> {
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
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionUtil?.let {
            when (requestCode) {
                IntentCode.CAMERA -> {
                    if (it.checkPermission(permissions)) {
                        intentCamera()
                    } else {
                        ToastUtil.show("没有${messageMap[missPermission]}权限")
                    }
                }

                IntentCode.STORAGE -> {
                    if (it.checkPermission(permissions)) {
                        intentPhoto()
                    } else {
                        ToastUtil.show("没有${messageMap[missPermission]}权限")
                    }
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

//        intent.putExtra("aspectX", aspectX)
//        intent.putExtra("aspectY", aspectY)
//        intent.putExtra("outputX", aspectX)
//        intent.putExtra("outputY", aspectY)
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        activity?.startActivityForResult(intent, IntentCode.CROP)
    }


    //设置裁剪最大像素
    fun setAspectXY(aspectX: Int, aspectY: Int) {
//        this.aspectX = aspectX
//        this.aspectY = aspectY
    }


    //初始化图片文件及URI
    private fun initFile() {
        file = FileUtils().getFile("${System.currentTimeMillis()}.jpg")
        file?.let { file ->
            uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                activity?.let {
                    FileProvider.getUriForFile(it, "${it.packageName}.provider", file)
                }

            } else {
                Uri.fromFile(file)
            }
        }


    }


    //裁剪结果回调
    interface OnResultListener {
        fun onResult(file: File)
    }


    fun onDestroy() {
        permissionUtil?.onDestroy()
        permissionUtil = null
        activity = null
        onResultListener = null
    }
}