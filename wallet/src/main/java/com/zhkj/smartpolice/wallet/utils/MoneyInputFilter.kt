package com.zhkj.smartpolice.wallet.utils

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

/**
 *
 */
class MoneyInputFilter : InputFilter {
    private var pattern: Pattern = Pattern.compile("[0-9]*") //除数字外的其他的
    private var pointLength = 2

    override fun filter(
        src: CharSequence, start: Int, end: Int,
        dest: Spanned, dstart: Int, dend: Int
    ): CharSequence? {

        val oldText = dest.toString()
        //验证删除等按键
        if (src.isEmpty()) {
            return null
        }

        if ("." == src.toString()) {
            return if (oldText.isEmpty()) {
                "0."
            } else {
                null
            }
        }

        if ("0" == src.toString()) {
            if (oldText.isEmpty()) {
                return "0."
            }
        }

        if (oldText == "0") {
            return ".$src"
        }

        //验证非数字或者小数点的情况
        val m = pattern.matcher(src)
        if (oldText.contains(".")) {
            //已经存在小数点的情况下，只能输入数字
            if (!m.matches()) {
                return ""
            }
        } else {
            if (!m.matches()) {
                return ""
            } else {
                if ("0" == src && "0" == oldText) {
                    return ""
                }
            }
            if ("." == src && oldText.isEmpty()) {
                return ""
            }
        }

        //验证小数位精度是否正确
        if (oldText.contains(".")) {
            val index = oldText.indexOf(".")
            val len = dend - index
            //小数位只能2位
            if (len > pointLength) {
                return dest.subSequence(dstart, dend)
            }
        }
        return dest.subSequence(dstart, dend).toString() + src.toString()
    }
}