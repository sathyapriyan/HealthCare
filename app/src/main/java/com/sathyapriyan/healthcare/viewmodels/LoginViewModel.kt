package com.sathyapriyan.healthcare.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sathyapriyan.healthcare.data.remote.NetworkResult
import com.sathyapriyan.healthcare.data.repository.HealthCareRepository
import com.sathyapriyan.healthcare.models.LoginResponse
import com.sathyapriyan.healthcare.models.requestbody.PatientLoginCredentials
import com.sathyapriyan.healthcare.util.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val healthCareRepository: HealthCareRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _authenticationResponse: MutableLiveData<NetworkResult<LoginResponse>> = MutableLiveData()
    val authenticationResponse = _authenticationResponse

    fun authenticatePatient(userName: String, password: String, context: Context) {

        println("Username and Password --> $userName and $password")

        _authenticationResponse.value = NetworkResult.Loading()

        if (CommonUtil.hasInternetConnection(context = context)) {

            viewModelScope.launch(ioDispatcher) {

                try {

                    val response = healthCareRepository.patientLoginRepo(
                        patientLoginCredentials = PatientLoginCredentials(
                            username = userName,
                            password = password
                        )
                    )

                    if (response.isSuccessful){

                        _authenticationResponse.postValue(
                            NetworkResult.Success(
                                data = response.body()!!
                            )
                        )

                    } else {

                        _authenticationResponse.postValue(NetworkResult.Error(response.message()))

                    }

                } catch (exception: Exception) {

                    _authenticationResponse.postValue(NetworkResult.Error(exception.message))

                }

            }

        } else {

            _authenticationResponse.value = NetworkResult.Error("No Internet Connection!")

        }

    }

}