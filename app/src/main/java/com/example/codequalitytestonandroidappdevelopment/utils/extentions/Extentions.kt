package com.example.codequalitytestonandroidappdevelopment.utils.extentions

import android.Manifest
import android.app.Activity
import android.graphics.Color
import android.view.View
import androidx.annotation.RequiresPermission
import com.example.codequalitytestonandroidappdevelopment.CodeQualityTestApp.Companion.getAppInstance
import com.example.codequalitytestonandroidappdevelopment.R
import com.google.android.material.snackbar.Snackbar

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun isNetworkAvailable(): Boolean {
    val info = getAppInstance().getConnectivityManager().activeNetworkInfo
    return info != null && info.isConnected
}
fun String.checkIsEmpty(): Boolean =
    isNullOrEmpty() || "" == this || this.equals("null", ignoreCase = true)
inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }

fun Activity.snackBarError(msg: String) {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
    val sbView = snackBar.view
    sbView.setBackgroundColor(getAppInstance().resources.getColor(R.color.colorPrimary))
    snackBar.setTextColor(Color.WHITE)
    snackBar.show()
}
