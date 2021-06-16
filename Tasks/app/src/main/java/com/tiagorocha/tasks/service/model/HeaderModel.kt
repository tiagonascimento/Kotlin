package com.tiagorocha.tasks.service.model

import com.google.gson.annotations.SerializedName

class HeaderModel  {
    @SerializedName("token")
    var token : String=""
    @SerializedName("name")
    var name :String =""
    @SerializedName("personKey")
    var personKey: String=""
}