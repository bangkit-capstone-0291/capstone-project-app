package com.bangkit.electrateam.qualityumapp.ui.camera

import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityCameraPreviewBinding
import com.bumptech.glide.Glide

class CameraPreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraPreviewBinding
    private lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriImage = intent.getStringExtra(EXTRA_IMAGE_CAPTURE)

        Glide.with(this)
            .load(Uri.parse(uriImage))
            .centerCrop()
            .placeholder(R.drawable.image_load)
            .into(binding.imgPreview)

        binding.btnClosePreview.setOnClickListener {
            finish()
        }

        binding.btnPredict.setOnClickListener {

        }
    }

    companion object {
        const val EXTRA_IMAGE_CAPTURE = "extra_image_capture"
        const val EXTRA_IMAGE_SELECTED = "extra_image_selected"
    }
}