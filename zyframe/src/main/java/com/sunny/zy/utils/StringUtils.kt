package com.sunny.zy.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 字符串工具类
 * Created by zhangye on 2018/6/11.
 */
object StringUtils {
    /**
     * 金额格式化
     * @param s 金额
     * @param len 小数位数
     * @return 格式后的金额
     */

    fun insertComma(money: String?, len: Int): String {
        if (money == null || money.isEmpty() || money == "0.0" || money == "0" || money == "0.00") {
            return "0.00"
        }
        val num = money.toDouble()
        val formater = if (len == 0) {
            DecimalFormat("###,###")

        } else {
            val buff = StringBuffer()
            buff.append("###,##0.")
            for (i in 0 until len) {
                buff.append("0")
            }
            DecimalFormat(buff.toString())
        }
        return formater.format(num)
    }


    fun insertComma(money: Double): String = insertComma(money.toString(), 2)
    fun insertComma(money: Float): String = insertComma(money.toString(), 2)


    fun isTimeOut(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val mDate = dateFormat.parse(date)
        val calendar = Calendar.getInstance()
        val currentDate = dateFormat.parse(dateFormat.format(calendar.time))
        return if (mDate.time < currentDate.time) {
            dateFormat.format(calendar.time)
        } else {
            date
        }
    }

    //根据长度生成随机字符串
    fun getRandomChar(length: Int): String {            //生成随机字符串
        val chr = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
        val random = Random()
        val buffer = StringBuffer()
        for (i in 0 until length) {
            buffer.append(chr[random.nextInt(36)])
        }
        return buffer.toString()
    }

    fun getNoncestr(): String {
        val min = 2
        val max = 20
        return getRandomChar(Random().nextInt(max - min) + min)
    }

    fun getTimeStamp():String =  (System.currentTimeMillis()/1000).toString()
}