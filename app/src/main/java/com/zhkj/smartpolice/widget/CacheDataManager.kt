package com.zhkj.smartpolice.widget

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Desc 缓存清理管理类
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/7/2 15:47
 */
object CacheDataManager {

    /**
     * 获取整体缓存大小
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getTotalCacheSize(context: Context): String {
        var cacheSize = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            context.externalCacheDir?.let {
                cacheSize += getFolderSize(it)
            }
        }
        return getFormatSize(cacheSize)
    }

    /**
     * 获取文件
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (value in fileList) {
                // 如果下面还有文件
                size = if (value.isDirectory) {
                    size + getFolderSize(value)
                } else {
                    size + value.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 格式化单位
     */
    private fun getFormatSize(size: Long): String {
        val kb = size / 1024
        val m = (kb / 1024).toInt()
        val kbs = (kb % 1024).toInt()
        return m.toString() + "." + kbs + "M"
    }

    /**
     * 清空方法
     */
    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.externalCacheDir)
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (child in children) {
                val success = deleteDir(File(dir, child))
                if (!success) {
                    return false
                }
            }
        }
        return dir!!.delete()
    }
}