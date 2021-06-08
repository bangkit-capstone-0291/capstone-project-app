package com.bangkit.electrateam.qualityumapp.ui.camera

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.data.remote.network.ApiResponse
import com.bangkit.electrateam.qualityumapp.databinding.ActivityCameraPreviewBinding
import com.bangkit.electrateam.qualityumapp.ui.add.fruits.AddFruitsActivity
import com.bangkit.electrateam.qualityumapp.ui.add.fruits.AddFruitsActivity.Companion.EXTRA_IMAGE_FRUITS
import com.bangkit.electrateam.qualityumapp.ui.add.fruits.AddFruitsActivity.Companion.EXTRA_PREDICT_RESULT
import com.bangkit.electrateam.qualityumapp.ui.add.fruits.AddFruitsActivity.Companion.EXTRA_QUALITY_RESULT
import com.bangkit.electrateam.qualityumapp.ui.camera.uploadimage.UploadRequest
import com.bangkit.electrateam.qualityumapp.ui.camera.uploadimage.Utils.getFileName
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CameraPreviewActivity : AppCompatActivity(), UploadRequest.UploadCallback {

    private lateinit var binding: ActivityCameraPreviewBinding
    private lateinit var cameraPreviewModel: CameraPreviewModel
    private var selectedImageUri: Uri? = null
    private var predictionResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        cameraPreviewModel = ViewModelProvider(this, factory)[CameraPreviewModel::class.java]

        val uriImage = intent.getStringExtra(EXTRA_IMAGE_PREVIEW)
        selectedImageUri = Uri.parse(uriImage)

        setDropDownMenu()

        Glide.with(this)
            .load(Uri.parse(uriImage))
            .centerCrop()
            .placeholder(R.drawable.image_load)
            .into(binding.imgPreview)

        binding.btnClosePreview.setOnClickListener {
            finish()
        }

        binding.btnPredict.setOnClickListener {
            uploadImage()
        }
    }

    private fun setDropDownMenu() {
        val items = listOf(
            getString(R.string.orange_type),
            getString(R.string.banana_type)
        )
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (binding.menuAddCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun uploadImage() {

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequest(file, "image", this)

        binding.progressBar.progress = 0

        val checkBtn = binding.checkPredict

        if (checkBtn.isChecked) {
            val fruitChoose = binding.autoCompleteCategory.text.toString()

            if (fruitChoose.isNotEmpty() && checkBtn.isChecked) {

                if (fruitChoose == getString(R.string.orange_type)) {
                    cameraPreviewModel.getOrangePrediction(file, body).observe(this, {
                        if (it != null) {
                            when (it) {
                                is ApiResponse.Success -> {
                                    binding.progressBar.progress = 100
                                    predictionResult = it.data.result
                                }

                                is ApiResponse.Error -> {
                                    binding.progressBar.progress = 0
                                    Toast.makeText(
                                        this,
                                        getString(R.string.upload_fail),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is ApiResponse.Empty -> Toast.makeText(
                                    this,
                                    getString(R.string.something_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                } else {
                    cameraPreviewModel.getBananaPrediction(file, body).observe(this, {
                        if (it != null) {
                            when (it) {
                                is ApiResponse.Success -> {
                                    binding.progressBar.progress = 100
                                    predictionResult = it.data.result
                                }

                                is ApiResponse.Error -> {
                                    binding.progressBar.progress = 0
                                    Toast.makeText(
                                        this,
                                        getString(R.string.upload_fail),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is ApiResponse.Empty -> Toast.makeText(
                                    this,
                                    getString(R.string.something_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }

                getClassification(file, body)

            } else Toast.makeText(this, getString(R.string.warning_type_choose), Toast.LENGTH_SHORT)
                .show()
        } else getClassification(file, body)

    }

    private fun getClassification(file: File, body: UploadRequest) {
        cameraPreviewModel.getClassification(file, body).observe(this, {
            if (it != null) when (it) {
                is ApiResponse.Success -> {
                    binding.progressBar.progress = 100
                    val nextIntent =
                        Intent(this@CameraPreviewActivity, AddFruitsActivity::class.java)
                    nextIntent.putExtra(EXTRA_QUALITY_RESULT, it.data.result)
                    nextIntent.putExtra(EXTRA_PREDICT_RESULT, predictionResult)
                    nextIntent.putExtra(EXTRA_IMAGE_FRUITS, selectedImageUri.toString())
                    startActivity(nextIntent)
                }

                is ApiResponse.Error -> {
                    binding.progressBar.progress = 0
                    Toast.makeText(
                        this,
                        getString(R.string.upload_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ApiResponse.Empty -> Toast.makeText(
                    this,
                    getString(R.string.something_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }

    companion object {
        const val EXTRA_IMAGE_PREVIEW = "extra_image_preview"
    }
}