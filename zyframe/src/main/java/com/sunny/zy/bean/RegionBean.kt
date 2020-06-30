package com.sunny.zy.bean

import com.google.gson.internal.LinkedTreeMap


class RegionBean {

    var provinceDistrictList: ArrayList<District>? = null   //管辖地
    var provinceList: ArrayList<Province>? = null           //省份
    var cityMap: LinkedTreeMap<String, ArrayList<City>>? = null//城市

    class District {
        var id: String? = null          //10000
        var name: String? = null        //全国
        var parentId: String? = null    // 1
        var createDate: String? = null  //2020-03-23 14:42:21
        var checked: Boolean = false    //false
    }

    class Province {
        var id: String? = null      //110000
        var name: String? = null    //"北京市"
        var checked: Boolean? = true//是否选中状态

        override fun toString(): String {
            return name ?: ""
        }
    }

    class City {
        var id: String? = null        //110101
        var name: String? = null      //"东城区"
        var parentId: String? = null  //110000
    }

}