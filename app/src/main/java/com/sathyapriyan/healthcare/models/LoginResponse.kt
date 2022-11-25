package com.sathyapriyan.healthcare.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(

    @SerializedName("success")
    var success: String,

    @SerializedName("msg")
    var message: String,

    @SerializedName("data")
    var patientData: PatientData

): Serializable {

    companion object {
        private const val serialVersionUID: Long = 123
    }

}

data class PatientData(

    @SerializedName("User")
    var user: User,
    @SerializedName("Currency")
    var currency: Currency,
    @SerializedName("PatientProfile")
    var patientProfile: PatientProfile,
    @SerializedName("PatientProfileLocation")
    var patientProfileLocation: PatientProfileLocation,
    @SerializedName("NotitificationDetails")
    var notitificationDetails: NotitificationDetails,
    @SerializedName("access_token")
    var accessToken:String,
    @SerializedName("userType")
    var userType:String,
    @SerializedName("device")
    var device: Device

)

data class User(
    @SerializedName("id")
    var id: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("hospital_id")
    var hospitalId: String,
    @SerializedName("avaya_ext")
    var avayaExt: String,
    @SerializedName("avaya_username")
    var avayaUsername: String,
    @SerializedName("avaya_password")
    var avayaPassword: String,
    @SerializedName("ribbon_ext")
    var ribbonExt: String,
    @SerializedName("ribbon_username")
    var ribbonUsername: String,
    @SerializedName("ribbon_password")
    var ribbonPassword: String
)

data class Currency(
    @SerializedName("symbol")
    var symbol: String,
    @SerializedName("prefix")
    var prefix: String,
    @SerializedName("hospital")
    var hospital: String,
    @SerializedName("country_id")
    var countryId: String
)

data class PatientProfile(
    @SerializedName("id")
    var id: String,
    @SerializedName("user_id")
    var userId: String,
    @SerializedName("salute")
    var salute: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("middle_name")
    var middleName: String,
    @SerializedName("last_name")
    var lastName: String,
    @SerializedName("suffix")
    var suffix: String,
    @SerializedName("display_name")
    var displayName: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("dob")
    var dob: String,
    @SerializedName("alternate_email")
    var alternateEmail: String,
    @SerializedName("sys_language_id")
    var sysLanguageId: String,
    @SerializedName("ssno")
    var ssno: String,
    @SerializedName("profile_picture")
    var profilePicture: String,
    @SerializedName("height")
    var height: String,
    @SerializedName("weight")
    var weight: String,
    @SerializedName("bmi")
    var bmi: String,
    @SerializedName("linkedin_url")
    var linkedinUrl: String,
    @SerializedName("website_url")
    var websiteUrl: String,
    @SerializedName("note")
    var note: String,
    @SerializedName("sys_ethnicity_id")
    var sysEthnicityId: String,
    @SerializedName("sys_race_id")
    var sysRaceId: String,
    @SerializedName("sys_time_zone_id")
    var sysTimeZoneId: String,
    @SerializedName("insurance_front_side")
    var insuranceFrontSide: String,
    @SerializedName("insurance_back_side")
    var insuranceBackSide: String,
    @SerializedName("driving_licence")
    var drivingLicence: String,
    @SerializedName("preferedlanguageid")
    var preferedLanguageId: String,
    @SerializedName("preferedlanguagename")
    var preferedLanguageName: String,
)

data class PatientProfileLocation(
    @SerializedName("id")
    var id: String,
    @SerializedName("user_id")
    var userId: String,
    @SerializedName("door_no")
    var doorNo: String,
    @SerializedName("street_name")
    var streetName: String,
    @SerializedName("landmark")
    var landmark: String,
    @SerializedName("locality")
    var locality: String,
    @SerializedName("postal_code")
    var postalCode: String,
    @SerializedName("phone1")
    var phone1: String,
    @SerializedName("phone2")
    var phone2: String,
    @SerializedName("notes")
    var notes: String,
    @SerializedName("country_id")
    var countryId: String,
    @SerializedName("country_name")
    var countryName: String,
    @SerializedName("state_id")
    var stateId: String,
    @SerializedName("state_name")
    var stateName: String,
    @SerializedName("city_id")
    var cityId: String,
    @SerializedName("city_name")
    var cityName: String
)

data class NotitificationDetails(

    @SerializedName("totalpush")
    var totalPush: String,
    @SerializedName("unread")
    var unread: String,
    @SerializedName("read")
    var read: String,
    @SerializedName("unnotified")
    var unNotified: String,
    @SerializedName("notified")
    var notified: String,
    @SerializedName("totalappointment")
    var totalAppointment: String,
    @SerializedName("totalcall")
    var totalCall: String

)

data class Device(
    @SerializedName("status")
    var status: String,
    @SerializedName("device_id")
    var deviceId: String,
    @SerializedName("os_id")
    var osId: String,
    @SerializedName("voip_token")
    var voipToken: String,
)

