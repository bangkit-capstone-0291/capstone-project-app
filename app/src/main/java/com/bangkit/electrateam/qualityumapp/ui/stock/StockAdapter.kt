package com.bangkit.electrateam.qualityumapp.ui.stock

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.databinding.ItemStockBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class StockAdapter : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var listMaterialStock = ArrayList<StockData>()

    fun setDataStock(stock: ArrayList<StockData>) {
        this.listMaterialStock.clear()
        this.listMaterialStock.addAll(stock)
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
        holder.bind(listMaterialStock[position])
    }

    override fun getItemCount(): Int = listMaterialStock.size

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