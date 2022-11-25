package com.sathyapriyan.healthcare.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sathyapriyan.healthcare.data.remote.NetworkResult
import com.sathyapriyan.healthcare.data.repository.HealthCareRepository
import com.sathyapriyan.healthcare.models.AddOrRemoveResponse
import com.sathyapriyan.healthcare.models.requestbody.AddOrRemoveCredentials
import com.sathyapriyan.healthcare.models.requestbody.PatientMyProviders
import com.sathyapriyan.healthcare.util.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorDetailViewModel @Inject constructor(
    private val healthCareRepository: HealthCareRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _addOrRemoveResponse: MutableLiveData<NetworkResult<AddOrRemoveResponse>> = MutableLiveData()
    val addOrRemoveResponse = _addOrRemoveResponse

    fun addOrRemoveFavourite(context: Context, patientUserId: String,
                             doctorUserId: String, isDeleted: Boolean,
    accessToken: String) {

        println("""
            patientUserId --> $patientUserId
            doctorUserId --> $doctorUserId
            isDeleted --> $isDeleted
        """.trimIndent())

        _addOrRemoveResponse.value = NetworkResult.Loading()

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {

                    val response = healthCareRepository.addOrRemoveFavouriteDoctorRepo(
                        accessToken = accessToken,
                        AddOrRemoveCredentials(
                            PatientMyProviders(
                                patientUserId = patientUserId,
                                doctorUserId = doctorUserId,
                                isDeleted = if (isDeleted) "yes" else "no"
                            )
                        )
                    )

                    if (response.isSuccessful){

                        _addOrRemoveResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _addOrRemoveResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _addOrRemoveResponse.postValue(NetworkResult.Error(exception.message))
                    println(exception.printStackTrace())

                }

            }

        } else {

            _addOrRemoveResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

}