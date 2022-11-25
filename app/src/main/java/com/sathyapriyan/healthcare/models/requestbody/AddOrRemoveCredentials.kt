package com.sathyapriyan.healthcare.models.requestbody

import com.google.gson.annotations.SerializedName

data class AddOrRemoveCredentials(

    @SerializedName("PatientMyProviders")
    var credential: PatientMyProviders

)

data class PatientMyProviders(

    @SerializedName("patient_user_id")
    var patientUserId: String,

    @SerializedName("doctor_user_id")
    var doctorUserId: String,

    @SerializedName("is_deleted")
    var isDeleted: String

)
