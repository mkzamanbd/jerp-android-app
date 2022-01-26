package com.example.simplelogin.ui.view.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplelogin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}