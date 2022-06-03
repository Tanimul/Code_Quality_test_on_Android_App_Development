package com.example.codequalitytestonandroidappdevelopment.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codequalitytestonandroidappdevelopment.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    companion object{
        private val TAG: String = "RegistrationActivity"
    }
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}