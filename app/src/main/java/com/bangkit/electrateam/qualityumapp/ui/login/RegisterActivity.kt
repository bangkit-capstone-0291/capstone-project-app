package com.bangkit.electrateam.qualityumapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.bangkit.electrateam.qualityumapp.MainActivity
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()

        binding.shorcutSignIn.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun register(){
        binding.signUp.setOnClickListener {
            if (TextUtils.isEmpty(binding.addFullname.text.toString())){
                binding.addFullname.error = resources.getString(R.string.errorfullname)
                return@setOnClickListener
            }else if (TextUtils.isEmpty(binding.addEmail.text.toString())){
                binding.addEmail.error = resources.getString(R.string.erroremail)
                return@setOnClickListener
            }else if (TextUtils.isEmpty(binding.addPassword.text.toString())){
                binding.addPassword.error = resources.getString(R.string.errorpassword)
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(binding.addEmail.text.toString(), binding.addPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val currentUsers = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUsers?.uid!!))
                        currentUserDb?.child("fullname")?.setValue(binding.addFullname.text.toString())

                        Toast.makeText(this@RegisterActivity, resources.getString(R.string.successregister), Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }else{
                        Toast.makeText(this@RegisterActivity, resources.getString(R.string.failedregister), Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}