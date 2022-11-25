package com.sathyapriyan.healthcare.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.sathyapriyan.healthcare.R
import com.sathyapriyan.healthcare.models.DoctorData
import com.sathyapriyan.healthcare.models.FavouriteDoctorData

object CommonUtil {

    fun hasInternetConnection(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork

        if (activeNetwork != null) {

            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

        }

        return false

    }

    fun ImageView.loadImageFromCoil(url: String?) {
        if(url!=null) {

            this.load(url){
                crossfade(true)
                placeholder(R.drawable.ic_user_image_place_holder)
                error(R.drawable.ic_user_image_place_holder)
                diskCachePolicy(CachePolicy.ENABLED)
                transformations(CircleCropTransformation())
            }

        }

    }

    fun mapAllDoctorDataToFavouriteDoctorData(allDoctorData: DoctorData): FavouriteDoctorData {

        return FavouriteDoctorData(
            id = allDoctorData.id,
            email = allDoctorData.email,
            specialty = allDoctorData.specialty,
            name = allDoctorData.name,
            profilePicture = allDoctorData.profilePicture,
            onlineStatus = allDoctorData.onlineStatus,
            fixedCharge = allDoctorData.fixedCharge,
            rating = allDoctorData.rating,
            additionalMinutes = allDoctorData.additionalMinutes,
            avayaExt = allDoctorData.avayaExt,
            avayaUsername = allDoctorData.avayaUsername,
            avayaPassword = allDoctorData.avayaPassword,
            fixedDuration = allDoctorData.fixedDuration
        )

    }

}