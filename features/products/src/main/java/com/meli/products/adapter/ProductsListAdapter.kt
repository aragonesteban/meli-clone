package com.meli.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.meli.domain.model.products.ProductItem
import com.meli.products.databinding.ItemProductBinding
import com.meli.shared.extensions.basicDiffUtil

class ProductsListAdapter(
    private val onItemClickProduct: (String) -> Unit
) : ListAdapter<ProductItem, ProductsListViewHolder>(
    basicDiffUtil { old, new -> old.id == new.id }
) {

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