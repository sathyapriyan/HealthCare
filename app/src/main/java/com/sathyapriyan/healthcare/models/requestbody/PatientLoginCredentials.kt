package com.sathyapriyan.healthcare.models.requestbody

import com.google.gson.annotations.SerializedName
import com.sathyapriyan.healthcare.util.Constants

data class PatientLoginCredentials(

    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("device_id")
    var deviceId: String = Constants.DEVICE_ID,

    @SerializedName("os_id")
    var osId: String = Constants.OS_ID,

    @SerializedName("time_zone")
    var timeZone: String = Constants.TIME_ZONE,

    @SerializedName("role_id")
    var roleId: String = Constants.ROLE_ID,

    @SerializedName("language")
    var language: String = Constants.LANGUAGE
)
