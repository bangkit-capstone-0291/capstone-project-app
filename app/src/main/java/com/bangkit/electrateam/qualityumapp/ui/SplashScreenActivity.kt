package com.bangkit.electrateam.qualityumapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.electrateam.qualityumapp.MainActivity
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_DELAY: Long = 2500
    }

    lateinit var auth: FirebaseAuth

    private val scopeActivity = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        scopeActivity.launch {
            delay(SPLASH_DELAY)

            auth = FirebaseAuth.getInstance()
            val currentUsers = auth.currentUser
            if (currentUsers != null) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            } else {
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}