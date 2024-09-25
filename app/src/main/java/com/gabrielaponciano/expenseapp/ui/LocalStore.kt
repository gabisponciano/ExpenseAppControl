package com.gabrielaponciano.expenseapp.ui

import android.content.Context

object LocalStore {
    private const val PREF_NAME = "Spending"

    fun saveToken(context: Context, value: String) {
        val editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.putString("token", value)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }
}