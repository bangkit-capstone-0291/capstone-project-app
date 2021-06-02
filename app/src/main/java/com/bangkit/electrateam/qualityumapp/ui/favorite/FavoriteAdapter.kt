package com.bangkit.electrateam.qualityumapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.electrateam.qualityumapp.R
import com.bangkit.electrateam.qualityumapp.data.StockData
import com.bangkit.electrateam.qualityumapp.databinding.ItemFavoriteBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var listFavStock = ArrayList<StockData>()

    fun setDataFav(favorite: ArrayList<StockData>) {
        this.listFavStock.clear()
        this.listFavStock.addAll(favorite)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        holder.bind(listFavStock[position])
    }

    override fun getItemCount(): Int = listFavStock.size

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemStock: StockData) {
            with(binding) {
                tvName.text = itemStock.name
                tvExpDate.text = itemStock.expDate
                tvTotalItem.text = itemStock.quantity.toString()
                tvCategory.text = itemStock.category

                Glide.with(itemView.context)
                    .load(itemStock.image)
                    .centerCrop()
                    .placeholder(R.drawable.image_load)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgItemFav)

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