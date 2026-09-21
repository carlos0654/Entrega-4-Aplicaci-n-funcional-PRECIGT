package com.estefanie.preciogt.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.theme.PrecioGtTheme

@Composable
fun ScannerScreen(modifier: Modifier = Modifier) {
    var scanned by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Escanea el código de barras",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Esta entrega simula el escáner con datos de ejemplo.",
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.QrCodeScanner,
                contentDescription = "Marco del escáner",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(110.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { scanned = true }) {
            Text(if (scanned) "Escanear de nuevo" else "Simular escaneo")
        }
        if (scanned) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Producto encontrado", fontWeight = FontWeight.Bold)
                    Text("Café Grano Orgánico 1kg")
                    Text(
                        "Mejor precio: Q115.00",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScannerScreenPreview() {
    PrecioGtTheme(dynamicColor = false) {
        ScannerScreen()
    }
}
