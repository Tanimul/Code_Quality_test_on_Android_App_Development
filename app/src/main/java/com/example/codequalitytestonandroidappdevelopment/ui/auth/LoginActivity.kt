package com.example.codequalitytestonandroidappdevelopment.ui.auth

import android.os.Bundle
import android.text.TextUtils
import com.example.codequalitytestonandroidappdevelopment.R
import com.example.codequalitytestonandroidappdevelopment.databinding.ActivityLoginBinding
import com.example.codequalitytestonandroidappdevelopment.ui.AppBaseActivity
import com.example.codequalitytestonandroidappdevelopment.ui.home.MapActivity
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.isNetworkAvailable
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.launchActivity
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppBaseActivity() {
    companion object {
        private val TAG: String = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.tvSignUp.setOnClickListener {
            launchActivity<RegistrationActivity>()
            finish()
        }

        binding.btSingIn.setOnClickListener {
            if (binding.checkboxStatus.isChecked) {
                var userEmail = binding.etUserEmail.text.toString()
                var password = binding.etPassword.text.toString()
                when {
                    TextUtils.isEmpty(userEmail) -> {
                        binding.etUserEmail.error = getString(R.string.enter_your_user_name)
                        binding.etUserEmail.requestFocus()
                    }
                    TextUtils.isEmpty(password) -> {
                        binding.etPassword.error = getString(R.string.password)
                        binding.etPassword.requestFocus()
                    }
                    !isNetworkAvailable() -> {
                        toast(getString(R.string.error_no_internet))
                    }
                    else -> {
                        firebaseAuth.signInWithEmailAndPassword(userEmail, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    if (firebaseAuth.currentUser!!.isEmailVerified) {
                                        toast("login successfully")
                                        launchActivity<MapActivity> { }
                                    } else {
                                        toast("Please Verify your Email address")
                                    }
                                } else {
                                    toast("login Unsuccessfully")
                                }
                            }
                    }
                }
            } else {
                toast("Please Agree the term and condition")
            }
        }
    }
}