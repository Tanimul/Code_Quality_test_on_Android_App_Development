package  com.example.codequalitytestonandroidappdevelopment.utils.extentions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

fun Context.getConnectivityManager() =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()