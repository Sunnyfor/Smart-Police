package com.zhkj.smartpolice.maintain.bean


class DepartmentStructureBean {
    var msg: String? = null
    var code: String? = null
    var data: ArrayList<DepartmentStructureInfo>? = null

    class DepartmentStructureInfo {
        var id: String? = null
        var name: String? = null
        var path: String? = null
        var children: ArrayList<ChildrenInfo>? = null
        var pid: String? = null

        override fun toString(): String {
            return "DepartmentStructureBean(id=$id, name=$name, path=$path, children=$children, pid=$pid)"
        }

        class ChildrenInfo {
            var id: String? = null
            var name: String? = null
            var path: String? = null
            var children: ArrayList<ChildrenInfo>? = null
            var pid: String? = null

            override fun toString(): String {
                return "ChildrenInfo(id=$id, name=$name, path=$path, children=$children, pid=$pid)"
            }

            class ChildrenInfo {
                var id: String? = null
                var name: String? = null
                var path: String? = null
                var children: Any? = null
                var pid: String? = null

                override fun toString(): String {
                    return "ChildrenInfo(id=$id, name=$name, path=$path, children=$children, pid=$pid)"
                }

            }

        }
    }

}