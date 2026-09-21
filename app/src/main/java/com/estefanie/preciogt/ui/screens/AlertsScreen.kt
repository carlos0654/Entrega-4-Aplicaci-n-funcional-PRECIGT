package com.estefanie.preciogt.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.theme.PrecioGtTheme

data class PriceAlert(
    val product: String,
    val store: String,
    val currentPrice: String,
    val targetPrice: String,
    val active: Boolean
)

private val mockAlerts = listOf(
    PriceAlert("Smartphone Pro Max 256GB", "TechStore", "Q6,899", "Q6,850", true),
    PriceAlert("Café Grano Orgánico 1kg", "Supermercado A", "Q115", "Q110", true),
    PriceAlert("Zapatillas Running Ultra", "DeportePro", "Q1,200", "Q1,100", false),
    PriceAlert("Smart TV 4K 55 pulgadas", "ElectroHogar", "Q7,500", "Q6,999", true)
)

@Composable
fun AlertsScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Mis avisos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Activa o pausa las alertas de precios de ejemplo.",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )
        }
        items(mockAlerts, key = { it.product }) { alert ->
            var enabled by rememberSaveable(alert.product) { mutableStateOf(alert.active) }
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsActive,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(alert.store.uppercase(), style = MaterialTheme.typography.labelSmall)
                        Text(alert.product, fontWeight = FontWeight.SemiBold)
                        Text(
                            "Actual ${alert.currentPrice} · Meta ${alert.targetPrice}",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Switch(checked = enabled, onCheckedChange = { enabled = it })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlertsScreenPreview() {
    PrecioGtTheme(dynamicColor = false) {
        AlertsScreen()
    }
}
