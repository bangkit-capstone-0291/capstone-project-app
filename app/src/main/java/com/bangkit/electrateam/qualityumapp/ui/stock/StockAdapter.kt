package com.bangkit.electrateam.qualityumapp.ui.stock

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.databinding.ItemStockBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*
import kotlin.collections.ArrayList

class StockAdapter : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var listMaterialStock = ArrayList<StockData>()
    private var filterList: List<StockData> = arrayListOf()

    init {
        filterList = listMaterialStock
    }

    fun setDataStock(stock: ArrayList<StockData>) {
        this.listMaterialStock.clear()
        this.listMaterialStock.addAll(stock)
        this.filterList = stock
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StockAdapter.StockViewHolder {
        val binding = ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockAdapter.StockViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    override fun getItemCount(): Int = filterList.size

    @Suppress("UNCHECKED_CAST")
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraints: CharSequence?): FilterResults {
                val charSearch = constraints.toString().lowercase(Locale.ROOT).trim()
                filterList = if (charSearch.isEmpty()) {
                    listMaterialStock
                } else {
                    val resultList = arrayListOf<StockData>()
                    for (item in listMaterialStock) {
                        if (item.name.lowercase(Locale.ROOT).contains(charSearch)) {
                            resultList.add(item)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraints: CharSequence?, results: FilterResults) {
                filterList = results.values as ArrayList<StockData>
                notifyDataSetChanged()
            }
        }
    }

    inner class StockViewHolder(private val binding: ItemStockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemStock: StockData) {
            with(binding) {
                tvTitleItem.text = itemStock.name
                tvTotalItem.text = itemStock.quantity.toString()
                tvCategory.text = itemStock.category

                Glide.with(itemView.context)
                    .load(Uri.parse(itemStock.image))
                    .centerCrop()
                    .placeholder(R.drawable.image_load)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgItem)

                itemView.setOnClickListener {
                    onItemClickListener.onItemClicked(itemStock)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(stock: StockData)
    }
}