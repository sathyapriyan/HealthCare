package com.sathyapriyan.healthcare.models.requestbody

import com.google.gson.annotations.SerializedName
import com.sathyapriyan.healthcare.util.Constants

data class UserIdCredential(

    @SerializedName("user_id")
    var userId: String = Constants.USER_ID

)
