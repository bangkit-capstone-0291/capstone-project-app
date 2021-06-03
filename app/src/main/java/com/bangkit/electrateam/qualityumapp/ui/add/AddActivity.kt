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
        supportActionBar?.title = getString(R.string.add_raw_material)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddViewModel::class.java]

        setDropDownMenu()
        onButtonSaveClicked()

        viewModel.observableState.observe(this, {
            check(it)
        })
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
            val category = binding.autoCompleteCategory.text.toString()
            val quantity = binding.addQuantity.text.toString().toInt()
            val expDate = binding.addExpDate.text.toString()
            val desc = binding.addDescription.text.toString()

            if (name != "" && quantity != 0) {
                val add = StockData(
                    id = id,
                    name = name,
                    image = 0,
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
}