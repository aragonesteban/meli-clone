package com.meli.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.meli.domain.model.products.ProductItem
import com.meli.products.adapter.ProductsListAdapter
import com.meli.products.databinding.FragmentProductsBinding
import com.meli.shared.extensions.showToastMessage
import com.meli.shared.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel by viewModels<ProductsViewModel>()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val productsListAdapter = ProductsListAdapter { productId ->
        goToProductDetail(productId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleClickListeners()
        setUpRecycler()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleViewState)
            }
        }
        viewModel.getProductListByQuery("tabla")
    }

    private fun handleClickListeners() {
        binding.apply {
            icMenu.setOnClickListener {
                requireContext().showToastMessage(getString(R.string.label_coming_soon))
            }
            icShoppingCart.setOnClickListener {
                requireContext().showToastMessage(getString(R.string.label_coming_soon))
            }
            searchProducts.setOnClickListener { goToSearch() }
        }
    }

    private fun setUpRecycler() {
        binding.productList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsListAdapter
        }
    }

    private fun handleViewState(uiState: ProductsUiState) {
        when (uiState) {
            ProductsUiState.Loading -> binding.loadingProducts.toggleVisibility(show = true)
            is ProductsUiState.ShowProductsList -> setDataPostsList(uiState.data)
            ProductsUiState.Error -> showErrorFeedback()
        }
    }


    private fun setDataPostsList(value: List<ProductItem>) {
        binding.loadingProducts.toggleVisibility(show = false)
        productsListAdapter.setProductsList(value)
    }


    private fun goToProductDetail(productId: String) {

    }

    private fun goToSearch() {

    }

    private fun showErrorFeedback() {
        requireContext().showToastMessage(getString(com.meli.shared.R.string.label_error))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}