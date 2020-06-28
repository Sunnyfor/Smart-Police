package com.zhkj.smartpolice.meal.bean

import java.io.Serializable

data class MealGoodsBean(
    var id: String,
    var title: String,
    var icon: Int,
    var count: Int,
    var weight: String,
    var price: String
) : Serializable