package com.bangkit.electrateam.qualityumapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.data.StockData
import com.bangkit.electrateam.qualityumapp.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateData(args.stock)
    }

    private fun populateData(data: StockData) {
        binding.apply {
            tvDetailName.text = data.name
            tvCategoryValue.text = data.category
            tvQualityValue.text = data.quality
            tvQuantityValue.text = data.quantity.toString()
            tvExpValue.text = data.expDate
            tvDescriptionValue.text = data.description
            Glide.with(this@DetailFragment)
                .load(data.image)
                .centerCrop()
                .placeholder(R.drawable.image_load)
                .into(imgDetailStock)
        }
    }

    private fun onFavButtonClicked(data: StockData) {
        binding.btnFavStock.setOnClickListener {
            /*viewModel.setFavStock()*/
            if (!data.isFavorite) Toast.makeText(
                context,
                data.name + " " + "Added to Favorite",
                Toast.LENGTH_SHORT
            ).show()
            else Toast.makeText(
                context,
                data.name + " " + "Deleted from Favorite",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setFavouriteState(state: Boolean) {
        if (state) {
            binding.btnFavStock.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.btnFavStock.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}