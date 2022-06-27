package com.meli.products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.meli.domain.model.products.ProductItem
import com.meli.products.adapter.ProductsListAdapter
import com.meli.products.databinding.FragmentProductsBinding
import com.meli.shared.SEARCH_QUERY_KEY
import com.meli.shared.extensions.showToastMessage
import com.meli.shared.extensions.toggleVisibility
import com.meli.shared.navigation.goToSearch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.meli.shared.R as SharedResource

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel by viewModels<ProductsViewModel>()

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val productsListAdapter = ProductsListAdapter { productId ->
        goToProductDetail(productId)
    }

    private var resultSearchActivity: ActivityResultLauncher<Intent>? = null

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
        onResultSearchActivity()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleViewState)
            }
        }
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
            ProductsUiState.Loading -> toggleLoading()
            ProductsUiState.ShowInitialEmptyState -> showInitialEmptyState()
            is ProductsUiState.ShowProductsList -> setDataPostsList(uiState.data)
            ProductsUiState.ShowEmptyStateProducts -> showEmptyStateProducts()
            ProductsUiState.Error -> showErrorFeedback()
        }
    }

    private fun toggleLoading() {
        binding.apply {
            loadingProducts.toggleVisibility(show = true)
            emptyState.toggleVisibility(show = false)
            productList.toggleVisibility(show = false)
        }
    }

    private fun showInitialEmptyState() {
        binding.apply {
            loadingProducts.toggleVisibility(show = false)
            emptyState.apply {
                toggleVisibility(show = true)
                setCustomImage(SharedResource.drawable.ic_search_meli)
                setCustomTitle(getString(SharedResource.string.label_start_search_products))
            }
        }
    }

    private fun setDataPostsList(value: List<ProductItem>) {
        binding.apply {
            productsListAdapter.setProductsList(value)
            loadingProducts.toggleVisibility(show = false)
            productList.toggleVisibility(show = true)
        }
    }

    private fun showEmptyStateProducts() {
        binding.apply {
            productList.toggleVisibility(show = false)
            loadingProducts.toggleVisibility(show = false)
            emptyState.apply {
                toggleVisibility(show = true)
                setCustomImage(SharedResource.drawable.ic_search_meli)
                setCustomTitle(getString(SharedResource.string.label_there_are_not_products))
                setCustomDescription(getString(SharedResource.string.label_check_word_wrote_correctly))
            }
        }
    }

    private fun goToProductDetail(productId: String) {

    }

    private fun goToSearch() {
        resultSearchActivity?.launch(Intent().goToSearch(requireContext()))
    }

    private fun showErrorFeedback() {
        requireContext().showToastMessage(getString(SharedResource.string.label_error))
    }

    private fun onResultSearchActivity() {
        resultSearchActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val query = result.data?.getStringExtra(SEARCH_QUERY_KEY) ?: String()
                    binding.apply {
                        emptyState.toggleVisibility(show = false)
                        searchProducts.setText(query)
                    }
                    viewModel.getProductListByQuery(query = query)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        resultSearchActivity = null
    }
}