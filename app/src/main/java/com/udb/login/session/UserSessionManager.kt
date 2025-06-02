package com.udb.login.session

import android.content.Context
import android.content.SharedPreferences

class UserSessionManager(context: Context) {
    private val prefsName = "user_session_prefs"
    private val keyEmail = "user_email"
    private val keyIsLoggedIn = "is_logged_in"

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun saveUserEmail(email: String) {
        prefs.edit().putString(keyEmail, email).apply()
    }

    fun getUserEmail(): String? {
        return prefs.getString(keyEmail, null)
    }

    fun setUserLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean(keyIsLoggedIn, loggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean(keyIsLoggedIn, false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}