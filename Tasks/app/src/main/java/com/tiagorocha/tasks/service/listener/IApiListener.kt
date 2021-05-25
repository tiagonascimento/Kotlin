package com.tiagorocha.tasks.service.listener

import com.tiagorocha.tasks.service.HeaderModel

interface IApiListener {
    fun  onSuccess(model: HeaderModel)
    fun  onFailure(mensage: String)
}