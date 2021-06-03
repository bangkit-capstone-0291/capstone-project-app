package com.bangkit.electrateam.qualityumapp.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.databinding.FragmentStockBinding
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory

class StockFragment : Fragment() {

    private lateinit var viewModel: StockViewModel
    private lateinit var stockAdapter: StockAdapter
    private var _binding: FragmentStockBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showLoading(true)

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[StockViewModel::class.java]
            val dataDummy = viewModel.getDummyStock()


            viewModel.getAllStock().observe(viewLifecycleOwner, {
                setData(it)
                showLoading(false)
                if (it.isEmpty()) showEmpty(true)
                else showEmpty(false)
            })

            stockAdapter = StockAdapter()
            onStockSelected()
            onFabClicked()
        }
    }

    private fun setData(data: List<StockData>) {
        stockAdapter.setDataStock(data as ArrayList<StockData>)
        with(binding.rvStock) {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = stockAdapter
        }
    }

    private fun onStockSelected() {
        stockAdapter.setOnItemClickListener(object : StockAdapter.OnItemClickListener {
            override fun onItemClicked(stock: StockData) {
                val action = stock.let { data ->
                    data.id?.let {
                        StockFragmentDirections.actionNavigationStockToDetailFragment(it)
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

    private fun onFabClicked() {
        binding.fab.setOnClickListener {
            val action = StockFragmentDirections.actionNavigationStockToAddActivity()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showEmpty(state: Boolean) {
        if (state) {
            binding.imgEmptyStock.visibility = View.VISIBLE
            binding.tvEmptyStock.visibility = View.VISIBLE
        } else {
            binding.imgEmptyStock.visibility = View.GONE
            binding.tvEmptyStock.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}