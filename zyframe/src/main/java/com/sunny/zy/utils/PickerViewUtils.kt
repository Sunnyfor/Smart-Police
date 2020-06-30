package com.sunny.zy.utils

import android.app.Activity
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.sunny.zy.R
import com.sunny.zy.bean.DictBean
import com.sunny.zy.bean.RegionBean
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Description:
 * @Author:         张野
 * @CreateDate:     2018/10/18 14:35
 */
class PickerViewUtils(var  activity: Activity) {

    private val textSize = 24
    private val titleSize = 18
    private val btnColor = ContextCompat.getColor(activity, R.color.font_black)
    private val bgColor = ContextCompat.getColor(activity,R.color.color_transparent)

    private var timePickerView: TimePickerView? = null
    private var singlePickerView: OptionsPickerView<DictBean>? = null
    private var addressPickerView: OptionsPickerView<RegionBean.Province>? = null

    /**
     * 日期选择器
     */
    private fun showDate(type: String, array: BooleanArray, onPickerViewResultListener: OnDataResultListener) {

        if (timePickerView != null) {
            timePickerView?.show()
            return
        }

        val startCalendar = Calendar.getInstance()
        startCalendar.set(1897, 0, 1)

        timePickerView = TimePickerBuilder(activity, OnTimeSelectListener { date, _ ->
            val dateFormat = SimpleDateFormat(type, Locale.getDefault())
            onPickerViewResultListener.onPickerViewResult(dateFormat.format(date))
        })
            .setContentTextSize(22)
            .setRangDate(startCalendar, Calendar.getInstance())
            .setType(array)
            .setDate(Calendar.getInstance())
            .setCancelColor(btnColor)
            .setSubmitColor(btnColor)
            .build()
        timePickerView?.show()
    }

    //显示年月
    fun showDateYYMM(onPickerViewResultListener: OnDataResultListener) {
        showDate("yyyy-MM", booleanArrayOf(true, true, false, false, false, false), onPickerViewResultListener)
    }

    //显示年月日
    fun showDateYYMMDD(onPickerViewResultListener: OnDataResultListener) {
        showDate("yyyy-MM-dd", booleanArrayOf(true, true, true, false, false, false), onPickerViewResultListener)
    }

    fun showReleaseTime(onPickerViewResultListener: OnDataResultListener) {
        if (timePickerView != null) {
            timePickerView?.show()
            return
        }

        val startCalendar = Calendar.getInstance()
        startCalendar.set(Calendar.DATE, startCalendar.get(Calendar.DATE))
        timePickerView = TimePickerBuilder(activity, OnTimeSelectListener { date, _ ->
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            onPickerViewResultListener.onPickerViewResult(dateFormat.format(date))
        })
            .setContentTextSize(textSize)
            .setRangDate(startCalendar, null)
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setCancelColor(btnColor)
            .setSubmitColor(btnColor)
            .build()
        timePickerView?.show()
    }


    /**
     * 单向选择器
     */
    fun showSingleChoice(hashMap: HashMap<String, String>?, onPickerViewResultListener: OnSingleChoiceResultListener) {

        val options1Items = ArrayList<DictBean>()
        hashMap?.entries?.forEach {
            options1Items.add(
                DictBean().apply {
                    id = it.key.toInt()
                    name = it.value
                }
            )
        }
        singlePickerView = OptionsPickerBuilder(activity, OnOptionsSelectListener { options1, _, _, _ ->
            //返回的分别是三个级别的选中位置
            onPickerViewResultListener.onPickerViewResult(options1Items[options1].id.toString(), options1Items[options1].name ?: "")

        })
            .setContentTextSize(textSize)
            .setCancelColor(btnColor)
            .setSubmitColor(btnColor)
            .setTitleColor(bgColor)
            .build()

        singlePickerView?.setPicker(options1Items)
        singlePickerView?.show()

    }

    /**
     * 地址选择器
     */
    fun showAddress(bean: RegionBean, isShowCity: Boolean, onPickerViewResultListener3: OnAddressResultListener) {
        val provinceList = bean.provinceList ?: arrayListOf()
        val cityList = arrayListOf<List<RegionBean.Province>>()

        if (isShowCity) {
            provinceList.forEach { province ->
                val list = ArrayList<RegionBean.Province>()
                bean.cityMap?.get(province.id)?.forEach {
                    val city = RegionBean.Province()
                    city.id = it.id
                    city.name = it.name
                    list.add(city)
                }

                cityList.add(list)
            }
        }

        addressPickerView = OptionsPickerBuilder(activity, OnOptionsSelectListener { options1, options2, _, _ ->
            //返回的分别是三个级别的选中位置
            if (isShowCity) {
                onPickerViewResultListener3.onPickerViewResult(
                    provinceList[options1].id ?: "",
                    provinceList[options1].name ?: "",
                    cityList[options1][options2].id ?: "",
                    cityList[options1][options2].name ?: "")
            } else {
                onPickerViewResultListener3.onPickerViewResult(
                    provinceList[options1].id ?: "",
                    provinceList[options1].name ?: "",
                    "",
                    "")
            }

        })
            .setContentTextSize(textSize)
            .setCancelColor(btnColor)
            .setSubmitColor(btnColor)
            .build()

        if (isShowCity) {
            addressPickerView?.setPicker(provinceList, cityList)
        } else {
            addressPickerView?.setPicker(provinceList)
        }

        addressPickerView?.show()
    }


    //回调结果
    interface OnDataResultListener {
        fun onPickerViewResult(value: String)
    }

    //回调结果
    interface OnSingleChoiceResultListener {
        fun onPickerViewResult(id: String, value: String)
    }

    //回调结果
    interface OnAddressResultListener {
        fun onPickerViewResult(provinceId: String, province: String, cityId: String, city: String)
    }
}