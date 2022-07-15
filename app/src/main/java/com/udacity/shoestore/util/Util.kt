package com.udacity.shoestore.util

import android.content.Context

object Util {
    fun getLoginStatus(context: Context): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(Constants.LOGIN_STATUS, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false)
    }

    fun saveLoginStatus(context: Context, status: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(Constants.LOGIN_STATUS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(Constants.LOGIN_STATUS, status)
        editor.apply()
    }
}