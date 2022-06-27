package com.meli.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.meli.domain.model.products.ProductItem
import com.meli.products.databinding.ItemProductBinding

class ProductsListAdapter(
    private val onItemClickProduct: (String) -> Unit
) : ListAdapter<ProductItem, ProductsListViewHolder>(ProductItemDiffCallback()) {

    fun setProductsList(value: List<ProductItem>) {
        submitList(value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsListViewHolder(
            binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickProduct)
    }

}

private class ProductItemDiffCallback : DiffUtil.ItemCallback<ProductItem>() {

    override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
        return oldItem == newItem
    }

}