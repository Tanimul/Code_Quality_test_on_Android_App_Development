package com.example.codequalitytestonandroidappdevelopment.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.codequalitytestonandroidappdevelopment.R
import com.example.codequalitytestonandroidappdevelopment.data.model.User
import com.example.codequalitytestonandroidappdevelopment.databinding.ActivityRegistrationBinding
import com.example.codequalitytestonandroidappdevelopment.ui.AppBaseActivity
import com.example.codequalitytestonandroidappdevelopment.utils.Constants.FirebaseKeys.KEY_USERS_COLLECTION
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.isNetworkAvailable
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.launchActivity
import com.example.codequalitytestonandroidappdevelopment.utils.extentions.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistrationActivity : AppBaseActivity() {
    companion object {
        private val TAG: String = "RegistrationActivity"
    }

    val db = Firebase.firestore
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar(binding.toolbar.toolbar)
        title = resources.getString(R.string.registration)

        firebaseAuth = FirebaseAuth.getInstance();

        binding.tvLogin.setOnClickListener {
            launchActivity<LoginActivity>()
            finish()
        }

        binding.btSingUp.setOnClickListener {
            if (binding.checkboxStatus.isChecked) {
                var userName = binding.etUserName.text.toString()
                var email = binding.etEmail.text.toString()
                var password = binding.etPassword.text.toString()
                when {
                    TextUtils.isEmpty(userName) -> {
                        binding.etUserName.error = getString(R.string.enter_your_user_name)
                        binding.etUserName.requestFocus()
                    }
                    TextUtils.isEmpty(email) -> {
                        binding.etEmail.error = getString(R.string.enter_your_email)
                        binding.etEmail.requestFocus()
                    }
                    TextUtils.isEmpty(password) -> {
                        binding.etPassword.error = getString(R.string.password)
                        binding.etPassword.requestFocus()
                    }
                    !isNetworkAvailable() -> {
                        toast(getString(R.string.error_no_internet))
                    }
                    else -> {
                        showProgress(true)
                        sendVerificationLink(email, password, userName)

                    }
                }
            } else {
                toast("Please Agree the term and condition")
            }
        }
    }

    private fun sendVerificationLink(email: String, password: String, userName: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                addToDatabase(it.result?.user, email, userName)
                firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        Log.d(TAG, "Registration processing .please verify the Email")
                        toast("Registration Successfully.please Verified your Email from your mail box")
                    } else {
                        Log.d(TAG, "Something went Wrong")
                        toast("Something went Wrong")
                    }
                }
            }
        }
    }

    private fun addToDatabase(
        f_user: FirebaseUser?,
        email: String,
        userName: String
    ) {
        if (f_user != null) {
            db.collection(KEY_USERS_COLLECTION).document(f_user.uid).get()
                .addOnSuccessListener { snapshot ->
                    //to check user exists or not
                    if (snapshot.exists()) {
                        /*User Already Exists*/

                        //hide progressbar
                        showProgress(false)
                    } else {
                        /*User Not Exists*/

                        //create new user object
                        val user = User(
                            _id = f_user.uid,
                            name = userName.trim(),
                            email = email
                        )

                        //store user info into database
                        db.collection(KEY_USERS_COLLECTION)
                            .document(f_user.uid)
                            .set(user).addOnSuccessListener {
                                //hide progressbar
                                showProgress(false)
                                onBackPressed()
                            }.addOnFailureListener {
                                showProgress(false)
                                toast(it.localizedMessage!!)
                            }
                    }
                }
        }
    }
}