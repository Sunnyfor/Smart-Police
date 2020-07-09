package com.zhkj.smartpolice.maintain.bean


class DepartmentStructureBean {
    var msg: String? = null
    var code: String? = null
    var data: ArrayList<DepartmentStructureInfo>? = null

    class DepartmentStructureInfo {
        var id: String? = null
        var name: String? = null
        var path: String? = null
        var children: String? = null
        var pid: String? = null

        override fun toString(): String {
            return "DepartmentStructureBean(id=$id, name=$name, path=$path, children=$children, pid=$pid)"
        }
    }

}