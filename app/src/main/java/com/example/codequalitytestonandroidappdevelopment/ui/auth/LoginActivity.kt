package com.example.codequalitytestonandroidappdevelopment.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codequalitytestonandroidappdevelopment.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    companion object{
        private val TAG: String = "LoginActivity"
    }
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}