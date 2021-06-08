package com.bangkit.electrateam.qualityumapp.ui.add.fruits

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.databinding.ActivityAddFruitsBinding
import com.bumptech.glide.Glide

class AddFruitsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFruitsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddFruitsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_raw_material)

        setDropDownMenu()

        val uriImage = intent.getStringExtra(EXTRA_IMAGE_FRUITS)
        Glide.with(this)
            .load(Uri.parse(uriImage))
            .centerCrop()
            .placeholder(R.drawable.image_load)
            .into(binding.imgAddFruits)

        val resultQuality = intent.getStringExtra(EXTRA_QUALITY_RESULT)
        binding.tvQualityPredict.text = resultQuality

        val resultPredict = intent.getIntExtra(EXTRA_PREDICT_RESULT, 0)
        binding.tvExpDatePredict.text = resultPredict.toString()
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_IMAGE_FRUITS = "extra_image_fruits"
        const val EXTRA_QUALITY_RESULT = "extra_quality_result"
        const val EXTRA_PREDICT_RESULT = "extra_predict_result"
    }
}