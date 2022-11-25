package com.sathyapriyan.healthcare.models

import com.google.gson.annotations.SerializedName

data class AddOrRemoveResponse(

    @SerializedName("success")
    var success: String,

    @SerializedName("msg")
    var message: String,

    @SerializedName("data")
    var addOrRemoveData: AddOrRemoveData

)

data class AddOrRemoveData(

    @SerializedName("success")
    var success: String,

    @SerializedName("msg")
    var message: String,

    @SerializedName("PatientMyProviders")
    var patientMyProvidersResponse: List<PatientMyProvidersResponse>

)

data class PatientMyProvidersResponse(

    @SerializedName("id")
    var id: String,

    @SerializedName("patient_user_id")
    var patientUserId: String,

    @SerializedName("doctor_user_id")
    var doctorUserId: String

)
