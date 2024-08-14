package tol.ideaplatform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.chip.Chip
import tol.ideaplatform.bd.Product
import tol.ideaplatform.ui.ProductViewModel
import tol.ideaplatform.ui.theme.IdeaPlatformTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IdeaPlatformTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IdeaPlatformTheme {
        Greeting("Android")
    }
}

@Composable
fun ProductListScreen(viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState()

    Column {
        TextField(
            value = "",
            onValueChange = { viewModel.searchProducts(it) },
            label = { Text("Search Products") }
        )
        LazyColumn {
            items(products) { product ->
                ProductCard(product = product, onDelete = { viewModel.deleteProduct(it) })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCard(product: Product, onDelete: (Product) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column {
            Text(text = product.name, style = MaterialTheme.typography.h6)
            Text(text = product.description)
            Text(text = "Price: $${product.price}")
            Text(text = "Quantity: ${product.quantity}")
            FlowRow {
                product.tags.forEach { tag ->
                    Chip(onClick = {}) {
                        Text(tag)
                    }
                }
            }
            Row {
                Button(onClick = { onDelete(product) }) {
                    Text("Delete")
                }
                // Кнопка для редактирования количества
            }
        }
    }
}