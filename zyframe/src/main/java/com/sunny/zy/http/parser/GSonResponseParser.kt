package com.sunny.zy.http.parser

import com.google.gson.Gson
import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.UrlConstant
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
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
        data: String,
        type: Type,
        serializedName: String?
    ): T {

        if (type is Class<*>) {
            when (type.name) {
                String::class.java.name -> {
                    return data as T
                }

                File::class.java.name -> {
                    return writeResponseBodyToDisk(data, serializedName) as T
                }
            }
        } else {
            if (type is ParameterizedType) {
                val jsonObj = JSONObject(data)
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
                        jsonObj.put("data", jsonObj.optJSONObject("page"))
                        jsonObj.remove("page")
                        return mGSon.fromJson(jsonObj.toString(), type)
                    }
                }
            }
        }
        return mGSon.fromJson(data, type)
    }


    private fun writeResponseBodyToDisk(data: String, serializedName: String?): File {
        val pathFile = File(UrlConstant.TEMP ?: "")
        if (!pathFile.exists()) {
            pathFile.mkdirs()
        }
        val file = File(pathFile, serializedName ?: "${System.currentTimeMillis()}.temp")
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()

        val writer = FileWriter(file)
        writer.write(data)
        writer.close()
        return file
    }

}