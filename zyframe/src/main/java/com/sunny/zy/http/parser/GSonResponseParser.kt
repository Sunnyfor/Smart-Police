package com.sunny.zy.http.parser

import com.google.gson.Gson
import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.UrlConstant
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/4/29 14:51
 */
@Suppress("UNCHECKED_CAST")
class GSonResponseParser : IResponseParser {

    private val mGSon = Gson()

    override fun <T> parserResponse(
        data: InputStream,
        type: Type,
        serializedName: String?
    ): T {

        if (type is Class<*>) {
            when (type.name) {
                String::class.java.name -> {
                    return writeString(data) as T
                }

                File::class.java.name -> {
                    return writeResponseBodyToDisk(data, serializedName) as T
                }
            }
        } else {
            if (type is ParameterizedType) {
                val jsonObj = JSONObject(writeString(data))
                when (type.rawType) {
                    BaseModel::class.java -> {
                        val childType = type.actualTypeArguments[0]
                        val baseModel = BaseModel<Any>()
                        baseModel.msg = jsonObj.optString("msg")
                        baseModel.code = jsonObj.optString("code")
                        val mData =
                            mGSon.fromJson<Any>(jsonObj.optString(serializedName), childType)
                        baseModel.data = mData
                        return baseModel as T
                    }
                    PageModel::class.java -> {
                        jsonObj.put("data", jsonObj.optJSONObject(serializedName))
                        jsonObj.remove(serializedName)
                        return mGSon.fromJson(jsonObj.toString(), type)
                    }
                }
            }
        }
        return mGSon.fromJson(writeString(data), type)
    }


    private fun writeString(data: InputStream): String {
        val byte = ByteArray(data.available())
        data.read(byte)
        return String(byte)
    }


    private fun writeResponseBodyToDisk(data: InputStream, serializedName: String?): File {
        val pathFile = File(UrlConstant.TEMP ?: "")
        if (!pathFile.exists()) {
            pathFile.mkdirs()
        }
        val file = File(pathFile, serializedName ?: "${System.currentTimeMillis()}.temp")
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        val byte = ByteArray(4096)
        var downloadSize = 0L

        val outputStream = FileOutputStream(file)
        while (true) {
            val read = data.read(byte)
            if (read == -1) {
                break
            }
            outputStream.write(byte, 0, read)
            downloadSize += read
        }
        outputStream.flush()
        return file
    }

}