package com.zhkj.smartpolice.mine.bean

/**
 * Desc 图片上传
 */
class ImageBean {

    var id: String? = null      // 图片id: 3477
    var format: String? = null  // 图片格式：image/png
    var name: String? = null    // 图片名称：q.png
    var src: String? = null     // 图片地址；/app/app/appfujian/download?attID=3477
    var fullName: String? = null// 图片路径：download/attachment/1577072623579.png
    var realName: String? = null// 图片名称：1577072623579.png
    var size: Int = 0           // 图片大小
    var userId: Int = 0
    var groupId: String? = null
    var userName: String? = null
    var remark: String? = null
    var updateDate: String? = null
    var activeState: Int = 0

    override fun toString(): String {
        return "ImageBean(id=$id, format=$format, name=$name, src=$src, fullName=$fullName, realName=$realName, size=$size, userId=$userId, groupId=$groupId, userName=$userName, remark=$remark, updateDate=$updateDate, activeState=$activeState)"
    }
}