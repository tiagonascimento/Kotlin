package com.tiagorocha.tasks.service.constants

class TaskConstants private constructor() {

    object SHAREND {
        const val TOKEN_KEY = "tokenkey"
        const val PERSON_KEY = "personkey"
        const val PERSON_NAME = "personname"
    }

    object HTTP {
        const val SUCCESS = 200
    }

    object HEADER {
        const val TOKEN_KEY = "token"
        const val PERSON_KEY = "personKey"
    }

    object BUNDLE {
        const val TASKID = "taskid"
        const val TASKFILTER = "taskfilter"

    }
    object FILTER {
        const val ALL = 0
        const val NEXT = 1
        const val EXPIRED = 2
    }

}