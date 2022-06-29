package com.meli.shared.extensions

import androidx.recyclerview.widget.DiffUtil

inline fun <T: Any> basicDiffUtil(
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areItemsTheSame: (T, T) -> Boolean
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}