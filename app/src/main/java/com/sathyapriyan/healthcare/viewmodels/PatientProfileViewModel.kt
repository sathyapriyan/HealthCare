package com.sathyapriyan.healthcare.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sathyapriyan.healthcare.data.remote.NetworkResult
import com.sathyapriyan.healthcare.data.repository.HealthCareRepository
import com.sathyapriyan.healthcare.models.DoctorListResponse
import com.sathyapriyan.healthcare.models.FavouriteDoctorListResponse
import com.sathyapriyan.healthcare.models.requestbody.UserIdCredential
import com.sathyapriyan.healthcare.util.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientProfileViewModel @Inject constructor(
    private val healthCareRepository: HealthCareRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _doctorListResponse: MutableLiveData<NetworkResult<DoctorListResponse>> = MutableLiveData()
    val doctorListResponse = _doctorListResponse

    private val _favouriteDoctorListResponse: MutableLiveData<NetworkResult<FavouriteDoctorListResponse>>
    = MutableLiveData()
    val favouriteDoctorListResponse = _favouriteDoctorListResponse

    fun getAllDoctorsDetails(context: Context) {

        _doctorListResponse.value = NetworkResult.Loading()

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {

                    val response = healthCareRepository.fetchAllDoctorDetailsRepo()

                    if (response.isSuccessful){

                        _doctorListResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _doctorListResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _doctorListResponse.postValue(NetworkResult.Error(exception.message))

                }

            }

        } else {

            _doctorListResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

    fun getFavouriteDoctorsDetails(accessToken: String, context: Context) {

        _favouriteDoctorListResponse.postValue(NetworkResult.Loading())

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {

                    val response = healthCareRepository.fetchPatientFavouriteDoctorsDetailsRepo(
                        accessToken = accessToken,
                        userIdCredential = UserIdCredential()
                    )

                    if (response.isSuccessful){

                        _favouriteDoctorListResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _favouriteDoctorListResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _favouriteDoctorListResponse.postValue(NetworkResult.Error(exception.message))

                }

            }

        } else {

            _favouriteDoctorListResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

}