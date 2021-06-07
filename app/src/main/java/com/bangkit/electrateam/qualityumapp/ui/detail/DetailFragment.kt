package com.bangkit.electrateam.qualityumapp.ui.detail

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.databinding.FragmentDetailBinding
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
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

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

            viewModel.setSelectedStock(args.id)
            viewModel.detailStock.observe(viewLifecycleOwner, {
                populateData(it)
                setFavouriteState(it.isFavorite)
                onFavButtonClicked(it)
            })
        }
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
                .load(Uri.parse(data.image))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_load)
                .into(imgDetailStock)
        }
    }

    private fun onFavButtonClicked(data: StockData) {
        binding.btnFavStock.setOnClickListener {
            viewModel.setFavStock(data)
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
        when (state) {
            true -> binding.btnFavStock.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            false -> binding.btnFavStock.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}