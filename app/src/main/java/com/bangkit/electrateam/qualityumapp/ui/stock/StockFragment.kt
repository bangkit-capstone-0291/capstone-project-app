package com.bangkit.electrateam.qualityumapp.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.electrateam.qualityumapp.data.StockData
import com.bangkit.electrateam.qualityumapp.databinding.FragmentStockBinding

class StockFragment : Fragment() {

    private lateinit var stockViewModel: StockViewModel
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
        val root: View = binding.root

        stockViewModel =
            ViewModelProvider(this).get(StockViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val dataStock = stockViewModel.getStock()

            stockAdapter = StockAdapter()
            setData(dataStock)
            onStockSelected()

            /*stockViewModel.text.observe(viewLifecycleOwner, {
            })*/
        }
    }

    private fun setData(data: List<StockData>) {
        showLoading(false)
        showEmpty(false)
        stockAdapter.setDataMovies(data as ArrayList<StockData>)
        with(binding.rvStock) {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = stockAdapter
        }
    }

    private fun onStockSelected() {
        stockAdapter.setOnItemClickListener(object : StockAdapter.OnItemClickListener {
            override fun onItemClicked(stock: StockData) {
                val action = stock.let {
                }
                action.let {
                }
            }
        })
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