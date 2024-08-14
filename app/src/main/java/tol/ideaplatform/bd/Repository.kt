package tol.ideaplatform.bd

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()
    fun searchProducts(query: String): Flow<List<Product>> = productDao.searchProducts(query)
    suspend fun insertProduct(product: Product) = productDao.insertProduct(product)
    suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
}