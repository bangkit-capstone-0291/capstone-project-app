package com.bangkit.electrateam.qualityumapp.ui.stock

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.databinding.FragmentStockBinding
import com.bangkit.electrateam.qualityumapp.viewmodel.ViewModelFactory

class StockFragment : Fragment() {

    private lateinit var viewModel: StockViewModel
    private lateinit var stockAdapter: StockAdapter
    private var _binding: FragmentStockBinding? = null
    private val args by navArgs<StockFragmentArgs>()
    private val binding get() = _binding!!
    private var category: Int = 0

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

            category = args.category

            if (category != 0) {

                when (category) {
                    10 -> {
                        viewModel.getAllCategory("Fruits").observe(viewLifecycleOwner, {
                            setData(it)
                            showLoading(false)
                            if (it.isEmpty()) showEmpty(true)
                            else showEmpty(false)
                        })
                    }

                    20 -> {
                        viewModel.getAllCategory("Vegetables").observe(viewLifecycleOwner, {
                            setData(it)
                            showLoading(false)
                            if (it.isEmpty()) showEmpty(true)
                            else showEmpty(false)
                        })
                    }

                    30 -> {
                        viewModel.getAllCategory("Others").observe(viewLifecycleOwner, {
                            setData(it)
                            showLoading(false)
                            if (it.isEmpty()) showEmpty(true)
                            else showEmpty(false)
                        })
                    }
                }
            } else {
                viewModel.getAllStock().observe(viewLifecycleOwner, {
                    setData(it)
                    showLoading(false)
                    if (it.isEmpty()) showEmpty(true)
                    else showEmpty(false)
                })
            }

            stockAdapter = StockAdapter()
            onStockSelected()
            onFabClicked()
            searching()
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

    private fun searching() {
        binding.searchType.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    val inputText = binding.searchType.text.toString()

                    if (inputText.isEmpty()) return@setOnKeyListener true
                    showLoading(true)
                    showEmpty(false)

                    stockAdapter.getFilter().filter(inputText)

                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        binding.searchField.setEndIconOnClickListener {

            val text = binding.searchField.editText?.text.toString()

            if (text.isEmpty()) return@setEndIconOnClickListener
            showLoading(true)
            showEmpty(false)

            stockAdapter.getFilter().filter(text)
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