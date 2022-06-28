package com.meli.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.meli.domain.model.products.ProductAttribute
import com.meli.domain.model.products.ProductDetail
import com.meli.productdetail.databinding.FragmentProductDetailBinding
import com.meli.shared.PRODUCT_ID_KEY
import com.meli.shared.extensions.formatToCurrency
import com.meli.shared.extensions.showToastMessage
import com.meli.shared.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels()

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = arguments?.getString(PRODUCT_ID_KEY) ?: String()

        setUpToolbar()
        viewModel.getProductDetailById(productId)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleViewState)
            }
        }
    }

    private fun setUpToolbar() {
        binding.toolbarProductDetail.apply {
            setNavigationIcon(com.meli.shared.R.drawable.ic_arrow_back)
            setNavigationOnClickListener { findNavController().popBackStack() }
            title = getString(R.string.label_product)
        }
    }

    private fun handleViewState(uiState: ProductDetailUiState) {
        when (uiState) {
            ProductDetailUiState.Loading -> binding.loadingProductDetail.toggleVisibility(show = true)
            is ProductDetailUiState.ShowProductDetail -> setDateProductDetail(uiState.data)
            ProductDetailUiState.Error -> showErrorFeedback()
        }
    }

    private fun setDateProductDetail(productDetail: ProductDetail) {
        binding.apply {
            loadingProductDetail.toggleVisibility(show = false)
            contentProductDetail.toggleVisibility(show = true)
            soldQuantityProductDetail.text =
                getString(R.string.label_sold_quantity, productDetail.soldQuantity)
            titleProductDetail.text = productDetail.title
            priceProductDetail.text = productDetail.price.toInt().formatToCurrency()
            stockAvailableProductDetail.text =
                getString(R.string.label_stock_available, productDetail.availableQuantity)

            showProductPictures(productDetail.picturesList.take(10))
            showProductAttributes(productDetail.attributes.take(10))
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    private fun showProductPictures(pictures: List<String>) {
        binding.apply {
            picturesProductDetail.setContent {
                val pagerState = rememberPagerState()
                countPicturesProductDetail.text =
                    "${pagerState.currentPage.plus(1)} / ${pictures.size}"
                HorizontalPager(count = pictures.size, state = pagerState) { page ->
                    AsyncImage(model = pictures[page], contentDescription = null)
                }
            }
        }
    }

    private fun showProductAttributes(productAttributes: List<ProductAttribute>) {
        binding.productAttributes.setContent {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = getString(R.string.label_attributes_product),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    fontFamily = Font(com.meli.shared.R.font.proxima_nova_regular).toFontFamily()
                )
                productAttributes.forEach { attribute -> ProductAttributeRow(attribute) }
            }
        }
    }

    @Composable
    private fun ProductAttributeRow(attribute: ProductAttribute) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = colorResource(id = com.meli.shared.R.color.meli_grey),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(20.dp)
            )
            TextAttribute(text = "${attribute.name}: ")
            TextAttribute(
                text = attribute.value ?: getString(R.string.label_no_apply),
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun TextAttribute(text: String, fontWeight: FontWeight = FontWeight.Normal) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            fontWeight = fontWeight,
            color = colorResource(id = com.meli.shared.R.color.meli_black),
            fontSize = 12.sp,
            fontFamily = Font(com.meli.shared.R.font.proxima_nova_regular).toFontFamily()
        )
    }

    private fun showErrorFeedback() {
        requireContext().showToastMessage(getString(com.meli.shared.R.string.label_error))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}