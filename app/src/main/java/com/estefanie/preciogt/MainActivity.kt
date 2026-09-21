package com.estefanie.preciogt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.estefanie.preciogt.navigation.PrecioGtApp
import com.estefanie.preciogt.ui.theme.PrecioGtTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrecioGtTheme(dynamicColor = false) {
                PrecioGtApp()
            }
        }
    }
}
