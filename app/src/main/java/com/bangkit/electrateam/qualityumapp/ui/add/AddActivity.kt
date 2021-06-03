package com.bangkit.electrateam.qualityumapp.ui.add

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityAddBinding
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var viewModel: AddViewModel
    private var stock: StockData? = null
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_raw_material)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddViewModel::class.java]

        setDropDownMenu()
        onButtonSaveClicked()

        viewModel.observableState.observe(this, {
            check(it)
        })

        binding.btnChoose.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.imgAddOthers.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun setDropDownMenu() {
        val items = listOf(
            getString(R.string.fruits),
            getString(R.string.vegetables),
            getString(R.string.others)
        )
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (binding.menuAddCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun onButtonSaveClicked() {
        binding.addButton.setOnClickListener {
            val id = if (stock != null) stock?.id else null
            val name = binding.addName.text.toString()
            val image = selectedImageUri.toString()
            val category = binding.autoCompleteCategory.text.toString()
            val quantity = binding.addQuantity.text.toString().toInt()
            val expDate = binding.addExpDate.text.toString()
            val desc = binding.addDescription.text.toString()

            if (name != "" && quantity != 0) {
                val add = StockData(
                    id = id,
                    name = name,
                    image = image,
                    category = category,
                    quantity = quantity,
                    expDate = expDate,
                    description = desc,
                    quality = " ",
                    isFavorite = false,
                )
                viewModel.addStock(add)
            } else Toast.makeText(
                this@AddActivity,
                "Please Enter Data Correctly!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun check(status: Boolean) {
        when (status) {
            true -> {
                finish()
                Toast.makeText(this, getString(R.string.add_success), Toast.LENGTH_SHORT).show()
            }
            false -> Toast.makeText(this, getString(R.string.add_failed), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
}