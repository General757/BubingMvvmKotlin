package com.bubing.kotlin.viewmodel.state

import androidx.databinding.ObservableField
import com.bubing.kotlin.data.model.bean.IntegralResponse
import com.bubing.mvvmk.base.viewmodel.BaseViewModel

/**
 * @ClassName: IntegralViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:21 PM
 * @Description: java类作用描述
 */
class IntegralViewModel : BaseViewModel() {

    var rank = ObservableField<IntegralResponse>()
}