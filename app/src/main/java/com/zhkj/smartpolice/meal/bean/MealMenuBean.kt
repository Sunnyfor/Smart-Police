package com.zhkj.smartpolice.meal.bean

data class MealMenuBean(
    var shopId: String?,
    var labelId: String?,
    var labelName: String?,//抗菌消炎
    var labelType: String?,
    var sort: String?,
    var publishState: String?,
    var createUserName: String?,//管理员
    var createUserId: String?,
    var createTime: String?,   //2020-06-29 16:45:45"
    var linkId: String?,
    var checked: String?,
    var goodsList: String?
)