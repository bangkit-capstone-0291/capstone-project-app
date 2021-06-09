package com.bangkit.electrateam.qualityumapp.ui.add.others

import android.app.DatePickerDialog
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityAddOthersBinding
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.receiver.AlarmReceiver
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddOthersActivity : AppCompatActivity() {

    private lateinit var othersViewModel: AddOthersViewModel
    private lateinit var alarmReceiver: AlarmReceiver
    private var _binding: ActivityAddOthersBinding? = null
    private val binding get() = _binding!!
    private var stock: StockData? = null
    private var selectedImageUri: Uri? = null
    private var dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private var initialValue = ContentValues()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddOthersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_raw_material)

        val factory = ViewModelFactory.getInstance(this)
        othersViewModel = ViewModelProvider(this, factory)[AddOthersViewModel::class.java]

        alarmReceiver = AlarmReceiver()

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                binding.imgAddOthers.setImageURI(uri)
                selectedImageUri = uri
            }

        setDropDownMenu()
        onButtonSaveClicked()

        othersViewModel.observableState.observe(this, {
            check(it)
        })

        binding.btnChoose.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.inputLayoutDate.setEndIconOnClickListener {
            pickDate()
        }
    }

    private fun pickDate() {
        val exptDate = Calendar.getInstance()
        val startYear = exptDate.get(Calendar.YEAR)
        val startMonth = exptDate.get(Calendar.MONTH)
        val startDay = exptDate.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, year, month, day ->
            val selectDate = Calendar.getInstance()
            selectDate.set(year, month, day)
            val showDateTime = dateFormat.format(selectDate.time)
            initialValue.put("exp_date", selectDate.timeInMillis)
            binding.addExpDate.setText(showDateTime)
        }, startYear, startMonth, startDay).show()
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
                othersViewModel.addStock(add)
            } else Toast.makeText(
                this@AddOthersActivity,
                getString(R.string.txt_data_not_correct),
                Toast.LENGTH_SHORT
            ).show()

            // set alarm
            val expiredD = initialValue.get("exp_date")

            binding.btnReminder.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmReceiver.setReminder(this, expiredD as Long, name)
                } else {
                    alarmReceiver.cancelReminder(this)
                }
            }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}