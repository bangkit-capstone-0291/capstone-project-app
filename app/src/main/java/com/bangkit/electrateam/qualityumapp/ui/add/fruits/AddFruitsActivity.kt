package com.bangkit.electrateam.qualityumapp.ui.add.fruits

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.electrateam.qualityumapp.MainActivity
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityAddFruitsBinding
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.receiver.AlarmReceiver
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class AddFruitsActivity : AppCompatActivity() {

    private lateinit var viewModel: AddFruitsViewModel
    private lateinit var alarmReceiver: AlarmReceiver
    private var _binding: ActivityAddFruitsBinding? = null
    private val binding get() = _binding!!
    private var stock: StockData? = null
    private var dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private var initialValue = ContentValues()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_raw_material)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddFruitsViewModel::class.java]

        alarmReceiver = AlarmReceiver()

        setDropDownMenu()

        viewModel.observableState.observe(this, {
            check(it)
        })

        val uriImage = intent.getStringExtra(EXTRA_IMAGE_FRUITS)
        val resultQuality = intent.getStringExtra(EXTRA_QUALITY_RESULT)
        val resultPredict = intent.getIntExtra(EXTRA_PREDICT_RESULT, 0)
        val code = intent.getIntExtra(EXTRA_PREDICT_CODE, 0)

        val expiredDate = System.currentTimeMillis() + (86400000 * resultPredict)

        initialValue.put("exp_date", expiredDate)

        if (uriImage != null && resultQuality != null) {
            setPredictCardValue(uriImage, resultQuality, expiredDate, code)
            onButtonSaveClicked(uriImage)
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

    private fun setPredictCardValue(image: String, quality: String, expiredDate: Long, code: Int) {
        binding.tvQualityPredict.text = quality

        Glide.with(this)
            .load(Uri.parse(image))
            .centerCrop()
            .placeholder(R.drawable.image_load)
            .into(binding.imgAddFruits)

        if (quality == "Banana Bad" || quality == "Orange Bad" || quality == "Apple Bad") {
            binding.tvReminderBad.visibility = View.VISIBLE
            binding.tvExpDatePredict.text = ""
            binding.btnReminder.visibility = View.GONE
        } else {
            binding.tvReminderBad.visibility = View.GONE
            when (code) {
                1 -> {
                    binding.tvExpDatePredict.text = ""
                    binding.btnReminder.visibility = View.GONE
                }
                2 -> {
                    binding.tvExpDatePredict.text = dateFormat.format(expiredDate)
                    binding.btnReminder.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onButtonSaveClicked(imageUri: String) {
        binding.addButton.setOnClickListener {
            val id = if (stock != null) stock?.id else null
            val name = binding.addName.text.toString()
            val category = binding.autoCompleteCategory.text.toString()
            val quantity = binding.addQuantity.text.toString().toInt()
            val quality = binding.tvQualityPredict.text.toString()
            val expDate = binding.tvExpDatePredict.text.toString()
            val desc = binding.addDescription.text.toString()
            if (name != "" && quantity != 0) {
                val add = StockData(
                    id = id,
                    name = name,
                    image = imageUri,
                    category = category,
                    quantity = quantity,
                    expDate = expDate,
                    description = desc,
                    quality = quality,
                    isFavorite = false,
                )
                viewModel.addStock(add)
            } else Toast.makeText(
                this@AddFruitsActivity,
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
                val outIntent = Intent(this@AddFruitsActivity, MainActivity::class.java)
                startActivity(outIntent)
                finish()
                Toast.makeText(this, getString(R.string.add_success), Toast.LENGTH_SHORT).show()
            }
            false -> Toast.makeText(this, getString(R.string.add_failed), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_IMAGE_FRUITS = "extra_image_fruits"
        const val EXTRA_QUALITY_RESULT = "extra_quality_result"
        const val EXTRA_PREDICT_RESULT = "extra_predict_result"
        const val EXTRA_PREDICT_CODE = "extra_predict_code"
    }
}