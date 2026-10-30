package com.estefanie.preciogt.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.components.ProductCard
import com.estefanie.preciogt.ui.components.SearchBar
import com.estefanie.preciogt.ui.theme.PrecioGtTheme

data class Product(
    val name: String,
    val store: String,
    val price: String
)

val mockProducts = listOf(
    Product("Leche Entera Larga Vida 1L", "Supermercado A", "Q12.50"),
    Product("Pan de Molde Integral 500g", "Hipermercado C", "Q18.00"),
    Product("Huevos Frescos Docena", "Tienda Vecina B", "Q28.90"),
    Product("Café Grano Orgánico 1kg", "Supermercado A", "Q115.00")
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    products: List<Product> = mockProducts
) {
    var query by rememberSaveable { mutableStateOf("") }
    val filteredProducts = remember(query, products) {
        if (query.isBlank()) products
        else products.filter {
            it.name.contains(query, ignoreCase = true) ||
                it.store.contains(query, ignoreCase = true)
        }
    }

    val context = LocalContext.current

    val prefs = remember(context) {
        context.getSharedPreferences(
            "PerfilUsuario",
            Context.MODE_PRIVATE
        )
    }
    var name = prefs.getString("name", "usuario nuevo")

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
                Text(
                    text = "¡Hola ${name}!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Compara precios y ahorra en tus compras de hoy.",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        item {
            Text(
                text = if (query.isBlank()) "Vistos recientemente" else "Resultados",
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (filteredProducts.isEmpty()) {
            item {
                Text(
                    text = "No hay productos que coincidan con la búsqueda.",
                    modifier = Modifier.padding(20.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            name = product.name,
                            store = product.store,
                            price = product.price
                        )
                    }
                }
            }
        }
        item {
            Text(
                text = "Mis carritos",
                modifier = Modifier.padding(start = 20.dp, top = 24.dp, end = 20.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Compra semanal", fontWeight = FontWeight.Bold)
                        Text("3 productos · Supermercado A")
                        Text(
                            "Q124.50",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Abrir carrito"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PrecioGtTheme(dynamicColor = false) {
        HomeScreen()
    }
}
