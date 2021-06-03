package com.bangkit.electrateam.qualityumapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.databinding.FragmentFavoriteBinding
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showLoading(true)

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            favoriteAdapter = FavoriteAdapter()
            onFavSelected()

            viewModel.getFavStock().observe(viewLifecycleOwner, {
                setDataFavorite(it)
                showLoading(false)
                if (it.isEmpty()) showEmpty(true)
                else showEmpty(false)
            })
        }
    }

    private fun setDataFavorite(data: List<StockData>) {
        favoriteAdapter.setDataFav(data as ArrayList<StockData>)
        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun onFavSelected() {
        favoriteAdapter.setOnItemClickListener(object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClicked(stock: StockData) {
                val action = stock.let { data ->
                    data.id?.let {
                        FavoriteFragmentDirections.actionNavigationFavoriteToDetailFragment(it)
                    }
                }
                action.let {
                    if (it != null) {
                        findNavController().navigate(it)
                    }
                }
            }
        })
    }

    private fun showEmpty(state: Boolean) {
        if (state) {
            binding.imgEmptyFav.visibility = View.VISIBLE
            binding.tvEmptyFav.visibility = View.VISIBLE
        } else {
            binding.imgEmptyFav.visibility = View.GONE
            binding.tvEmptyFav.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}