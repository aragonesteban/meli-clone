package com.meli.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.shared.databinding.CustomViewEmptyStateBinding
import com.meli.shared.extensions.toggleVisibility

class CustomEmptyStateBuilder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr){

    private val binding =
        CustomViewEmptyStateBinding.inflate(LayoutInflater.from(context), this, true)

    fun setCustomImage(image: Int) {
        binding.illustrationEmptyState.setBackgroundResource(image)
    }

    fun setCustomTitle(title: String)  {
        binding.titleEmptyState.text = title
    }

    fun setCustomDescription(description: String) {
        binding.descriptionEmptyState.apply {
            text = description
            toggleVisibility(show = true)
        }
    }

    fun setCustomButtonClick(onButtonClick: () -> Unit) {
        binding.btnEmptyState.apply {
            setOnClickListener { onButtonClick.invoke() }
            toggleVisibility(show = true)
        }
    }
}