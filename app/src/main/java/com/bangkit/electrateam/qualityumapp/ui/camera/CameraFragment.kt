package com.bangkit.electrateam.qualityumapp.ui.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.databinding.FragmentCameraBinding
import com.bangkit.electrateam.qualityumapp.ui.camera.uploadimage.UploadRequest
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class CameraFragment : Fragment(),  UploadRequest.UploadCallback{

    companion object{
        const val EXTRA_CAMERA = 1
        const val EXTRA_IMG = 101
    }

    private lateinit var previewModel: CameraPreviewModel
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        previewModel = ViewModelProvider(this).get(CameraPreviewModel::class.java)
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openGallery.setOnClickListener {
            openImageChooser()
        }
        binding.openCamera.setOnClickListener {
            openCamera()
        }
        binding.btnUpload.setOnClickListener {
            uploadImage()
        }
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, EXTRA_IMG)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                startActivityForResult(takePictureIntent, EXTRA_CAMERA)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                EXTRA_IMG -> {
                    selectedImageUri = data?.data
                    binding.imageResult.setImageURI(selectedImageUri)
                }
                EXTRA_CAMERA -> {
//                    if (resultCode == Activity.RESULT_OK && requestCode == EXTRA_CAMERA && data != null){
//                        binding.imageResult.setImageBitmap(data.extras?.get("data") as Bitmap)
//                    }
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    selectedImageUri = getImageUri(requireContext(), imageBitmap)
                    binding.imageResult.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun getImageUri(context: Context, image: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            image,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    private fun uploadImage() {

        /*val parcelFileDescriptor =
            context?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(requireContext().cacheDir, requireContext().contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        binding.progressBar.progress = 0
        val body = UploadRequest(file, "image", this)
        API().uploadImage(
            MultipartBody.Part.createFormData(
                "image",
                file.name,
                body
            ),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "json")
        ).enqueue(object : Callback<ResponseFromUpload> {
            override fun onFailure(call: Call<ResponseFromUpload>, t: Throwable) {
                binding.result.snackbar(t.message!!)
                binding.progressBar.progress = 0
            }

            override fun onResponse(
                call: Call<ResponseFromUpload>,
                response: Response<ResponseFromUpload>
            ) {
                response.body()?.let {
                    binding.result.snackbar(it.toString())
                    binding.progressBar.progress = 100
                }
            }
        })*/
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}