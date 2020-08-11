package com.bubing.kotlin.data.model.bean

import com.bubing.kotlin.data.model.enums.MeItemType

/**
 * @ClassName: MeItemEntity
 * @Author: Bubing
 * @Date: 2020/8/11 2:31 PM
 * @Description: java类作用描述
 */
data class MeItemEntity(
    var itemType: MeItemType,
    var itemPosition: Int,
    var data: Any
)