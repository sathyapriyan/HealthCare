package com.sathyapriyan.healthcare.util

import androidx.datastore.preferences.core.stringPreferencesKey

object SharedPreferenceKeys {

    // Preference File
    const val USER_PREFERENCES_FILE = "user_info"
    const val COMMON_PREFERENCE_FILE = "common_info"

    // User Preference Keys
    val USER_ACCESS_TOKEN = stringPreferencesKey("access_token")

}