package com.meli.products.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meli.domain.HASH
import com.meli.domain.model.products.ProductItem
import com.meli.products.databinding.ItemProductBinding
import com.meli.shared.extensions.formatToCurrency
import java.text.DecimalFormat

class ProductsListViewHolder(
    private val binding: ItemProductBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productItem: ProductItem, onItemClickProduct: (String) -> Unit) {
        binding.apply {
            root.setOnClickListener {
                onItemClickProduct.invoke(productItem.id)
            }
            thumbnailProductItem.load(productItem.thumbnail) { crossfade(true) }
            titleProductItem.text = productItem.title
            priceProductItem.text = productItem.price.toInt().formatToCurrency()
            installmentsProductItem.text =
                " ${productItem.duesQuantity}x ${removeDecimalsOfInstallment(productItem.duesAmount)}"
        }
    }

    private fun removeDecimalsOfInstallment(amount: Double): String {
        val decimalFormat = DecimalFormat(HASH).format(amount)
        return decimalFormat.toInt().formatToCurrency()
    }

}