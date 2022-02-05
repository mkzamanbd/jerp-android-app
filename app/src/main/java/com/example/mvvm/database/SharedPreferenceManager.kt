package com.example.mvvm.database

import android.content.Context
import android.content.SharedPreferences
import com.example.mvvm.data.response.TokenDataModel
import com.example.mvvm.data.response.UserDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private val rememberMe = "remember_me" //remember current user credential
    private val loginInfo = "login_info" //store login information
    private val areaInfo = "area_info" //store login information
    private val token = "token" //store token information
    private val loginStatus = "login" //store token information
    private val territoryInfo = "territory_info" //store territory info
    private val demoMode = "demo_mode"

    /**
     * Store user name and password
     * @param isRemember check box status, when true store user credential, otherwise not
     * @param userName entered user name
     * @param password entered password
     */
    fun rememberUserCredential(isRemember: Boolean, userName: String, password: String) {
        sharedPreferences = appContext.getSharedPreferences(rememberMe, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putBoolean("isRemember", isRemember)
        editor.putString("userName", userName)
        editor.putString("password", password)
        editor.apply()
    }

    /**
     * ...update password
     * @param password update password
     */
    fun updateRememberPassword(password: String?) {
        sharedPreferences = appContext.getSharedPreferences(rememberMe, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("password", password)
        editor.apply()
    }

    /**
     * save is user logged in
     * @param isLoggedIn log in status
     */
    fun isLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences = appContext.getSharedPreferences(loginStatus, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    /**
     * save logged in user information
     * @param user logged in user info
     */
    fun storeUserInformation(user: UserDataModel) {
        sharedPreferences = appContext.getSharedPreferences(loginInfo, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("userId", user.id)
        editor.putString("hashUid", user.userId)
        editor.putString("userType", user.userType)
        editor.putString("userName", user.userName)
        editor.putString("name", user.name)
        editor.putString("email", user.email)
        editor.putString("phone", user.phone)
        editor.putString("photo", user.photo)
        editor.putInt("roleId", user.roleId)
        editor.putString("roleName", user.roleName)
        editor.putString("visibleName", user.visibleName)
        editor.putString("sbuId", user.sbuId)
        editor.putString("sbuName", user.sbuName)
        editor.apply()
    }

    fun storeTokenInformation(tokenDataModel: TokenDataModel) {
        sharedPreferences = appContext.getSharedPreferences(token, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("accessToken", tokenDataModel.accessToken)
        editor.putString("tokenType", tokenDataModel.tokenType)
        editor.putString("expireDate", tokenDataModel.expireAt)
        editor.apply()
    }

    /**
     * get user remember status
     * @return if remember then return true otherwise false
     */
    fun getRememberStatus(): Boolean {
        sharedPreferences = appContext.getSharedPreferences(rememberMe, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isRemember", false)
    }

    /**
     * get last remembered user name
     * @return user name
     */
    fun getRememberUsername(): String? {
        sharedPreferences = appContext.getSharedPreferences(rememberMe, Context.MODE_PRIVATE)
        return sharedPreferences.getString("userName", null)
    }

    /**
     * ...get current user name
     * @return user name
     */
    fun getUserName(): String? {
        sharedPreferences = appContext.getSharedPreferences(loginInfo, Context.MODE_PRIVATE)
        return sharedPreferences.getString("userName", null)
    }

    /**
     * get last remembered password
     * @return user password
     */
    fun getRememberPassword(): String? {
        sharedPreferences = appContext.getSharedPreferences(rememberMe, Context.MODE_PRIVATE)
        return sharedPreferences.getString("password", null)
    }

    /**
     * get user login status
     * @return true if user logged in otherwise return false
     */
    fun getIsUserLoggedIn(): Boolean {
        sharedPreferences = appContext.getSharedPreferences(loginStatus, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    /**
     * ...get user access token
     * @return user access token
     */
    fun userAccessToken(): String? {
        sharedPreferences = appContext.getSharedPreferences(token, Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }

    /**
     * ...get user full name
     * @return user full name
     */
    fun getUserFullName(): String? {
        sharedPreferences = appContext.getSharedPreferences(loginInfo, Context.MODE_PRIVATE)
        return sharedPreferences.getString("name", null)
    }

    /**
     * ...get user role name
     * @return user role name
     */
    fun getUserRoleName(): String? {
        sharedPreferences = appContext.getSharedPreferences(loginInfo, Context.MODE_PRIVATE)
        return sharedPreferences.getString("visibleName", null)
    }

    /**
     * ...clear all data while user logged out
     */
    fun clearAll() {
        val prefNames = arrayOf<String>(
            loginStatus,
            territoryInfo,
            token,
            loginInfo,
            demoMode
        )
        for (prefName in prefNames) {
            sharedPreferences = appContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
        }
    }

}