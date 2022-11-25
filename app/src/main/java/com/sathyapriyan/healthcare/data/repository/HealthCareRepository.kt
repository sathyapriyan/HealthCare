package com.sathyapriyan.healthcare.data.repository

import com.sathyapriyan.healthcare.data.remote.HealthCareApi
import com.sathyapriyan.healthcare.models.AddOrRemoveResponse
import com.sathyapriyan.healthcare.models.DoctorListResponse
import com.sathyapriyan.healthcare.models.FavouriteDoctorListResponse
import com.sathyapriyan.healthcare.models.LoginResponse
import com.sathyapriyan.healthcare.models.requestbody.AddOrRemoveCredentials
import com.sathyapriyan.healthcare.models.requestbody.PatientLoginCredentials
import com.sathyapriyan.healthcare.models.requestbody.UserIdCredential
import retrofit2.Response
import javax.inject.Inject

class HealthCareRepository @Inject constructor(
    private val healthCareApi: HealthCareApi
) {

    suspend fun patientLoginRepo(
        patientLoginCredentials: PatientLoginCredentials
    ): Response<LoginResponse> {

        return healthCareApi.patientLogin(patientLoginCredentials = patientLoginCredentials)

    }

    suspend fun fetchAllDoctorDetailsRepo(): Response<DoctorListResponse> {

        return healthCareApi.fetchAllDoctorDetails()

    }

    suspend fun fetchPatientFavouriteDoctorsDetailsRepo(
        accessToken: String,
        userIdCredential: UserIdCredential
    ): Response<FavouriteDoctorListResponse> {

        return healthCareApi.fetchPatientFavouriteDoctorsDetails(
            "Bearer $accessToken",
            userIdCredential = userIdCredential)

    }

    suspend fun addOrRemoveFavouriteDoctorRepo(
        accessToken: String,
        addOrRemoveCredentials: AddOrRemoveCredentials
    ): Response<AddOrRemoveResponse> {

        return healthCareApi.addOrRemoveFavouriteDoctor(
            "Bearer $accessToken",
            addOrRemoveCredentials = addOrRemoveCredentials)

    }

}