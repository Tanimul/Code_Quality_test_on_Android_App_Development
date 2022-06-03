package  com.example.codequalitytestonandroidappdevelopment.utils.extentions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.codequalitytestonandroidappdevelopment.CodeQualityTestApp
import com.example.codequalitytestonandroidappdevelopment.CodeQualityTestApp.Companion.getAppInstance
import com.example.codequalitytestonandroidappdevelopment.CodeQualityTestApp.Companion.noInternetDialog
import com.example.codequalitytestonandroidappdevelopment.R
import com.example.codequalitytestonandroidappdevelopment.databinding.LayoutNoInternetBinding
import com.example.codequalitytestonandroidappdevelopment.utils.SharedPrefUtils
inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

inline fun <reified T : Any> Activity.launchActivityWithNewTask() {
    launchActivity<T> {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

fun getSharedPrefInstance(): SharedPrefUtils {
    return if (CodeQualityTestApp.sharedPrefUtils == null) {
        CodeQualityTestApp.sharedPrefUtils = SharedPrefUtils()
        CodeQualityTestApp.sharedPrefUtils!!
    } else {
        CodeQualityTestApp.sharedPrefUtils!!
    }

}

enum class JsonFileCode {
    NO_INTERNET,
    LOADER
}

fun Activity.openLottieDialog(
    jsonFileCode: JsonFileCode = JsonFileCode.NO_INTERNET,
    onLottieClick: () -> Unit
) {
    try {
        val jsonFile: String = when (jsonFileCode) {
            JsonFileCode.NO_INTERNET -> "lottie/no_internet.json"
            JsonFileCode.LOADER -> "lottie/loader.json"
        }
        val dialogLayout = LayoutNoInternetBinding.inflate(this.layoutInflater)

        if (noInternetDialog == null) {
            noInternetDialog = Dialog(this, R.style.FullScreenDialog)
            noInternetDialog?.setContentView(dialogLayout.root)
            noInternetDialog?.setCanceledOnTouchOutside(false)
            noInternetDialog?.setCancelable(false)
            noInternetDialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialogLayout.parentNoInternet.onClick {
                if (!isNetworkAvailable()) {
                    snackBarError(getAppInstance().getString(R.string.error_no_internet))
                    return@onClick
                }
                noInternetDialog?.dismiss()
                onLottieClick()
            }
        }
        dialogLayout.lottieNoInternet.setAnimation(jsonFile)
        if (!this.isFinishing && !noInternetDialog!!.isShowing) {
            noInternetDialog?.show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}