package com.bangkit.electrateam.qualityumapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.bangkit.electrateam.qualityumapp.MainActivity
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        actionButtonLogin()
        binding.shortcutSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun actionButtonLogin(){
        binding.signIn.setOnClickListener {
            if (TextUtils.isEmpty(binding.addEmail.text.toString())){
                binding.addEmail.error = resources.getString(R.string.erroremail)
                return@setOnClickListener
            }else if (TextUtils.isEmpty(binding.addPassword.text.toString())) {
                binding.addPassword.error = resources.getString(R.string.errorpassword)
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(binding.addEmail.text.toString(), binding.addPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, resources.getString(R.string.failedlogin), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}