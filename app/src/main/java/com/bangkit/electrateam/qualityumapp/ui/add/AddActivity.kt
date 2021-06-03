package com.bangkit.electrateam.qualityumapp.ui.add

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddViewModel::class.java]

        val items = listOf(
            getString(R.string.fruits),
            getString(R.string.vegetables),
            getString(R.string.others)
        )
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        (binding.menuAddCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        onButtonSaveClicked()
    }

    private fun onButtonSaveClicked() {
        binding.addButton.setOnClickListener {
            val id = if (stock != null) stock?.id else null
            with(binding) {
                val name = addName.text.toString()
                val category = autoCompleteCategory.text.toString()
                val quantity = addQuantity.text
                val expDate = addExpDate.text.toString()
                val desc = addDescription.text.toString()

                if (name != "" && quantity != null) {
                    val add = id?.let { id ->
                        StockData(
                            id = id,
                            name = name,
                            image = 0,
                            category = category,
                            quantity = quantity as Int,
                            expDate = expDate,
                            description = desc,
                            quality = "",
                            isFavorite = false,
                        )
                    }
                    if (add != null) {
                        viewModel.addStock(add)
                    }
                } else Toast.makeText(
                    this@AddActivity,
                    "Please Enter Data Correctly!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            viewModel.observableState.observe(this, {
                check(it)
            })
        }
    }

    private fun check(status: Boolean) {
        when (status) {
            true -> {
                finish()
                Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
            }
            false -> Toast.makeText(this, "Failed to Add Item", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}