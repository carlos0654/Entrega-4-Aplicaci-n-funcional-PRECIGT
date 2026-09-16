package com.estefanie.preciogt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.components.ProductCard
import com.estefanie.preciogt.ui.components.SearchBar

data class Product(
    val imageUrl: String,
    val name: String,
    val price: String
)

val mockProducts = listOf(
    Product(
        imageUrl = "https://picsum.photos/seed/leche/200",
        name = "Leche Entera 1L - Lala",
        price = "Q12.50"
    ),
    Product(
        imageUrl = "https://picsum.photos/seed/pan/200",
        name = "Pan Integral - Las Victorias",
        price = "Q18.00"
    ),
    Product(
        imageUrl = "https://picsum.photos/seed/huevos/200",
        name = "Huevos Extra Grande - Walmart",
        price = "Q28.90"
    ),
    Product(
        imageUrl = "https://picsum.photos/seed/arroz/200",
        name = "Arroz Gallo Dorado 1lb - Hiper Paiz",
        price = "Q9.75"
    ),
    Product(
        imageUrl = "https://picsum.photos/seed/frijol/200",
        name = "Frijol Negro 1lb - MaxiDespensa",
        price = "Q11.25"
    )
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    products: List<Product> = mockProducts
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.padding(top = 16.dp)
        )

        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(products) { product ->
                ProductCard(
                    imageUrl = product.imageUrl,
                    name = product.name,
                    price = product.price
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}