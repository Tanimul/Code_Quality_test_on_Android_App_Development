package com.example.codequalitytestonandroidappdevelopment

import android.app.Application
import android.app.Dialog
import android.content.Context
import com.example.codequalitytestonandroidappdevelopment.utils.SharedPrefUtils

class CodeQualityTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {
        private lateinit var appInstance: CodeQualityTestApp

        var sharedPrefUtils: SharedPrefUtils? = null
        var noInternetDialog: Dialog? = null

        fun getAppInstance(): CodeQualityTestApp {
            return appInstance
        }

    }
}