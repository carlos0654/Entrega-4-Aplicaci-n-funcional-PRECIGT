package com.estefanie.preciogt.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.estefanie.preciogt.ui.components.CartCard
import com.estefanie.preciogt.ui.components.ProductCard
import com.estefanie.preciogt.ui.components.SearchBar
import java.time.LocalDate

data class Cart(
    val name: String,
    val size: Int,
    val lastUpdate: LocalDate,
    val bestPrice: String,
    val store: String,
)

@RequiresApi(Build.VERSION_CODES.O)
val mockCart = listOf(
    Cart("MI CARRITO", 12,LocalDate.of(2025,11,11),"Q123.00", "Walmart"),
    Cart("Supermercado",6,LocalDate.of(2025,11,11), "Q250.00", "La Torre"),
    Cart("Compras mensuales 1", 30,LocalDate.of(2025,11,11),"Q345.00", "La torre"),
    Cart("Compras 2", 17,LocalDate.of(2025,11,11),"Q432.22", "Pais"),
    Cart("Compras 2", 17,LocalDate.of(2025,11,11),"Q432.22", "Pais"),
    Cart("Compras 2", 17,LocalDate.of(2025,11,11),"Q432.22", "Pais")
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartScreen(navController: NavController,
               modifier: Modifier = Modifier,
               products: List<Product> = mockProducts,
               carts: List<Cart> = mockCart)
{
    var query by rememberSaveable { mutableStateOf("") }

    val filteredProducts = remember(query, products) {
        if (query.isBlank()) products
        else products.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.store.contains(query, ignoreCase = true)
        }
    }
    val filteredCart = remember(query, carts) {
        if (query.isBlank()) carts
        else carts.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.store.contains(query, ignoreCase = true)
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        SearchBar(
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier.padding(top = 12.dp)
        )

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredCart) { carts ->
                CartCard(
                    name = carts.name,
                    size = carts.size,
                    lastUpdate = carts.lastUpdate,
                    bestPrice = carts.bestPrice,
                    store = carts.store,
                )
            }
        }
    }
    /*
    FloatingActionButton(onClick = { }
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }*/
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = { navController.navigate("product")},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Agregar"
            )
        }
    }

}