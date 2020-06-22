package com.sunny.zy.utils

import android.os.Environment
import com.sunny.zy.ZyFrameStore
import java.io.File
import java.text.DecimalFormat

/**
 * 文件操作相关工具类
 */
class FileUtils {

    /**
     * 获取换成你文件路径
     */
    fun getCacheDir(): File {
        val file = File(
            Environment.getExternalStorageDirectory(),
            "${ZyFrameStore.getContext()?.packageName}/temp"
        )
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }


    /**
     * 根据文件名获取文件
     */
    fun getFile(name: String): File {
        return File(getCacheDir(), name)
    }


    /**
     * 获取目录缓存大小
     */
    fun getCacheSize(): Long {
        var size = 0L
        size += getFolderSize(getCacheDir())
        return size
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    fun formatFileSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        val fileSizeString: String
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        fileSizeString = when {
            fileS < 1024 -> df.format(fileS.toDouble()) + "B"
            fileS < 1048576 -> df.format(fileS.toDouble() / 1024) + "KB"
            fileS < 1073741824 -> df.format(fileS.toDouble() / 1048576) + "MB"
            else -> df.format(fileS.toDouble() / 1073741824) + "GB"
        }
        return fileSizeString
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (aFileList in fileList!!) {
                size += if (aFileList.isDirectory) {
                    getFolderSize(aFileList)
                } else {
                    aFileList.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }


    /**
     * 删除所有缓存文件
     */
    fun deleteAllFile() {
        getCacheDir().listFiles().forEach {
            deleteFolderFile(it.path)
        }
    }

    private fun deleteFolderFile(filePath: String) {
        val file = File(filePath)
        if (file.isDirectory) {
            val files = file.listFiles()
            for (file1 in files) {
                deleteFolderFile(file1.absolutePath)
            }
        } else {
            file.delete()
        }
    }

}