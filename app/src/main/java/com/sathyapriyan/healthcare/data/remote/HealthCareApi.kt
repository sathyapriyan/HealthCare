package com.sathyapriyan.healthcare.data.remote

import com.sathyapriyan.healthcare.models.AddOrRemoveResponse
import com.sathyapriyan.healthcare.models.DoctorListResponse
import com.sathyapriyan.healthcare.models.FavouriteDoctorListResponse
import com.sathyapriyan.healthcare.models.LoginResponse
import com.sathyapriyan.healthcare.models.requestbody.AddOrRemoveCredentials
import com.sathyapriyan.healthcare.models.requestbody.PatientLoginCredentials
import com.sathyapriyan.healthcare.models.requestbody.UserIdCredential
import com.sathyapriyan.healthcare.util.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface HealthCareApi {

    @POST(Constants.PATIENT_LOGIN_URL)
    suspend fun patientLogin(
        @Body patientLoginCredentials: PatientLoginCredentials
    ): Response<LoginResponse>

    @POST(Constants.DOCTOR_LIST_URL)
    suspend fun fetchAllDoctorDetails(): Response<DoctorListResponse>

    @POST(Constants.FAVOURITE_DOCTOR_LIST_URL)
    suspend fun fetchPatientFavouriteDoctorsDetails(
        @Header(Constants.AUTHORIZATION_KEY) accessToken: String,
        @Body userIdCredential: UserIdCredential
    ): Response<FavouriteDoctorListResponse>

    @POST(Constants.ADD_OR_REMOVE_FAVOURITE_DOCTOR_URL)
    suspend fun addOrRemoveFavouriteDoctor(
        @Header(Constants.AUTHORIZATION_KEY) accessToken: String,
        @Body addOrRemoveCredentials: AddOrRemoveCredentials
    ): Response<AddOrRemoveResponse>

}