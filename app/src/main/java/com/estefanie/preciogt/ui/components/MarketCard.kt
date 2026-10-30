package com.estefanie.preciogt.ui.components

import android.R
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import java.time.LocalDate


@Composable
fun MarketCard(
    name: String,
    img: String,
    nowPrice: String,
    bestPrice: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row( modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically) {



            Box(modifier = Modifier.size(50.dp).padding(10.dp)) {

                Column(modifier = Modifier.fillMaxSize()) {
                        /*
                        Icon(
                            imageVector = Icons.Outlined.ShoppingBag,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                                .weight(1f)
                                .fillMaxHeight()
                        )*/

                    AsyncImage(
                        model = img,
                        contentDescription = "Imagen",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )


                }


            }



            Column(verticalArrangement = Arrangement.Center) {
                Text(text = name, maxLines = 2, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = nowPrice,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.padding(end = 3.dp)) {
                if (bestPrice){
                    Icon(
                        imageVector = Icons.Outlined.AutoGraph,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }

            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MarketCardPreview() {
    MarketCard(
        name = "Walmart",
        img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLzLH5K_3YILPl4KOmkFw5VqdZ07WHZ7QDbsYuUoh-blVMbJkl3z0XPuU&s=10",
        nowPrice = "Q100.00",
        bestPrice = true
    )
}
