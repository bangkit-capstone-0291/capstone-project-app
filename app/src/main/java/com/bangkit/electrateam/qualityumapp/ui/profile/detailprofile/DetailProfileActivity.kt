package com.bangkit.electrateam.qualityumapp.ui.profile.detailprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityDetailProfileBinding

class DetailProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detail_profile)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}