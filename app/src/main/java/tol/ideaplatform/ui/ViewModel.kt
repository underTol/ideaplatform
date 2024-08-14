package tol.ideaplatform.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import tol.ideaplatform.bd.Product
import tol.ideaplatform.bd.ProductRepository
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    val products = repository.getAllProducts().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private val _searchQuery = MutableStateFlow("")
    val searchResults = _searchQuery.flatMapLatest { query ->
        repository.searchProducts(query)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun searchProducts(query: String) {
        _searchQuery.value = query
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        repository.insertProduct(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        repository.deleteProduct(product)
    }
}