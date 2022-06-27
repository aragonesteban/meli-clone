package com.meli.search

import android.app.Activity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.meli.search.databinding.ActivitySearchBinding
import com.meli.shared.SEARCH_QUERY_KEY
import com.meli.shared.extensions.showToastMessage
import com.meli.shared.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar()
        setSearchViewListener()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSearchHistory()
                viewModel.uiState.collect(::handleViewState)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbarSearch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setSearchViewListener() {
        binding.searchInMeli.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { goBackToSearchProduct(query) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun handleViewState(uiState: SearchUiState) {
        when (uiState) {
            SearchUiState.Loading -> binding.loadingSearchHistory.toggleVisibility(show = true)
            is SearchUiState.ShowSearchHistory -> setDataSearchHistory(uiState.data)
            SearchUiState.Error -> showToastMessage(getString(com.meli.shared.R.string.label_error))
        }
    }

    private fun goBackToSearchProduct(query: String) {
        viewModel.saveSearchQuery(query)
        intent.putExtra(SEARCH_QUERY_KEY, query)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setDataSearchHistory(searchHistory: List<String>) {
        binding.apply {
            loadingSearchHistory.toggleVisibility(show = false)
            searchHistoryList.setContent {
                MaterialTheme {
                    LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                        items(searchHistory) { query -> ItemSearchHistory(query) }
                    }
                }
            }
        }
    }

    @Composable
    fun ItemSearchHistory(query: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { goBackToSearchProduct(query) }
                .padding(16.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = null,
                    tint = colorResource(id = com.meli.shared.R.color.meli_grey)
                )
                Text(
                    text = query,
                    style = MaterialTheme.typography.body2,
                    color = colorResource(id = com.meli.shared.R.color.meli_grey),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_north_west),
                contentDescription = null,
                tint = colorResource(id = com.meli.shared.R.color.meli_grey)
            )
        }
    }

}