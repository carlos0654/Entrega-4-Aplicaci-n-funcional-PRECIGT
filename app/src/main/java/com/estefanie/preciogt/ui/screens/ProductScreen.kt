package com.estefanie.preciogt.ui.screens

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.estefanie.preciogt.ui.components.MarketCard
import com.estefanie.preciogt.ui.components.PriceChart
import kotlin.collections.List


data class Market(
    val name: String,
    val img: String,
    val nowPrice: String,
    val bestPrice: Boolean
)

data class PrecioPorFecha(
    val fecha: String,
    val precioA: Float,
    val precioB: Float
)


val mockMarket = listOf(
    Market("Walmart",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLzLH5K_3YILPl4KOmkFw5VqdZ07WHZ7QDbsYuUoh-blVMbJkl3z0XPuU&s=10",
        "Q100.00",
         true
    ),
    Market("Torre",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyYbmHUD_xNM1SflKvuz3lIs-3oTmjQzzzl99fPMU61PDslldn1U1V-Uz8&s=10",
        "Q120.00",
        false
    )

)

val mockPrecios =
    listOf(
        PrecioPorFecha("18/10", 125.00f, 130.00f),
        PrecioPorFecha("19/10", 127.50f, 119.00f),
        PrecioPorFecha("20/10", 130.00f, 115.00f),
        PrecioPorFecha("26/10", 122.50f, 119.50f),
        PrecioPorFecha("28/10", 110.00f, 125.00f),
        PrecioPorFecha("29/10", 100.00f, 120.50f)
    )



@Composable
fun ProductScreen(modifier: Modifier = Modifier,
                  markets: List<Market> = mockMarket,
                  preciosEjemplo : List<PrecioPorFecha> = mockPrecios){
    var query by rememberSaveable { mutableStateOf("") }
    var nameProduct ="COCACOLA EN LATA"
    val filteredProducts = remember(query, markets) {
        if (query.isBlank()) markets
        else markets.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Box(modifier = Modifier.size(250.dp).padding(20.dp)) {

                Column(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = "https://comerciovending.com/18743-medium_default/cocacola-original-330ml.jpg",
                        contentDescription = "Imagen",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }


            }
            Column() {
                Text(text = nameProduct, maxLines = 2, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = "Q100.00",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Row() {
                Button(
                    onClick = { },
                    modifier = Modifier.absoluteOffset(30.dp,)
                        .padding(10.dp)
                        .fillMaxHeight()
                ) {
                    Icon(
                        Icons.Filled.AddAlert,
                        contentDescription = "Agregar"
                    )
                }

                Button(
                    onClick = { },
                    modifier = Modifier.absoluteOffset(30.dp,)
                        .padding(10.dp)
                        .fillMaxHeight()
                ) {
                    Text(text = "Agregar a carrito")
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Agregar"
                    )
                }
            }

        }


        item{
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Historial de precios",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Precio en quetzales · Octubre de 2026",
                    style = MaterialTheme.typography.bodyMedium
                )


            }
        }

        item{
            PriceChart(
                precios = preciosEjemplo,
                nombreSupermercadoA = "Walmart",
                nombreSupermercadoB = "La Torre",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }



                items(filteredProducts) { markets ->
                    MarketCard(
                        name = markets.name,
                        img = markets.img,
                        nowPrice = markets.nowPrice,
                        bestPrice = markets.bestPrice
                    )
                }




        //


    }
    val backDispatcher =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Box(
        modifier = Modifier.size(70.dp)
    ) {
        FloatingActionButton(
            onClick = { backDispatcher?.onBackPressed() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            Icon(
                Icons.Filled.ArrowBackIosNew,
                contentDescription = "Agregar"
            )
        }
    }

}



