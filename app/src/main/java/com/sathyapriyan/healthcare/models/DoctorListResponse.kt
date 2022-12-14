package com.sathyapriyan.healthcare.models

import com.google.gson.annotations.SerializedName

data class DoctorListResponse(

    @SerializedName("success")
    var success: String,

    @SerializedName("msg")
    var message: String,

    @SerializedName("data")
    var doctorData: List<DoctorData>

    )

data class DoctorData(

    @SerializedName("id")
    var id: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("specialty")
    var specialty: List<Specialty>,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("profile_picture")
    var profilePicture: String,
    @SerializedName("online_status")
    var onlineStatus: String,
    @SerializedName("busy_on")
    var busyOn: String,
    @SerializedName("rating")
    var rating: String,
    @SerializedName("fixed_charge")
    var fixedCharge: String,
    @SerializedName("additional_minutes")
    var additionalMinutes: String,
    @SerializedName("avaya_ext")
    var avayaExt: String,
    @SerializedName("avaya_username")
    var avayaUsername: String,
    @SerializedName("avaya_password")
    var avayaPassword: String,
    @SerializedName("state_id")
    var stateId: String,
    @SerializedName("fixed_duration")
    var fixedDuration: String

    /*@SerializedName("favourite")
    var favourite: String,
    @SerializedName("state")
    var state: String,*/
)

data class Specialty(

    @SerializedName("id")
    var id: String,

    @SerializedName("name")
    var name: String

)

