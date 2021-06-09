package com.bangkit.electrateam.qualityumapp.ui.profile.detailprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityDetailProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DetailProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProfileBinding
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail_profile)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        loadProfile()
    }

    private fun loadProfile() {
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        binding.profileEmail.text = user?.email
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profileName.text = snapshot.child("fullname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}