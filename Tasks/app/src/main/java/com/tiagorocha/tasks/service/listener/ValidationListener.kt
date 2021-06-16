package com.tiagorocha.tasks.service.listener

class ValidationListener(str: String = "") {
    private var _status = true
    private var _message = ""
    init {
        if (str != "") {
            _status = false
            _message = str
        }
    }
    fun success() = _status
    fun failure() = _message
}