package com.tiagorocha.tasks.service.listener

import com.tiagorocha.tasks.service.model.HeaderModel

interface IApiListener<T> {
    fun  onSuccess(model: T)
    fun  onFailure(mensage: String)

}