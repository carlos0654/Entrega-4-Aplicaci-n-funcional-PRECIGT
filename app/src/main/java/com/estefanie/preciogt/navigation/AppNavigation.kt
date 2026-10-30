package com.estefanie.preciogt.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.estefanie.preciogt.ui.screens.AccountScreen
import com.estefanie.preciogt.ui.screens.AlertsScreen
import com.estefanie.preciogt.ui.screens.CartScreen
import com.estefanie.preciogt.ui.screens.HomeScreen
import com.estefanie.preciogt.ui.screens.ProductScreen
import com.estefanie.preciogt.ui.screens.ScannerScreen
import com.estefanie.preciogt.ui.screens.ScannerScreen2
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.estefanie.preciogt.ui.data.FirestoreRepository
import com.estefanie.preciogt.ui.viewmodel.ProfileViewModel
import com.estefanie.preciogt.ui.viewmodel.ProfileViewModelFactory

private data class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val destinations = listOf(
    Destination("home", "Inicio", Icons.Outlined.Home),
    Destination("carts", "Carritos", Icons.Outlined.ShoppingCart),
    Destination("scanner", "Escáner", Icons.Outlined.QrCodeScanner),
    Destination("alerts", "Avisos", Icons.Outlined.NotificationsNone),
    Destination("account", "Cuenta", Icons.Outlined.AccountCircle)
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrecioGtApp() {
    val profileRepository = remember {
        FirestoreRepository()
    }
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("carts") { CartScreen(navController = navController) }
            composable("scanner") { ScannerScreen2() }
            composable("alerts") { AlertsScreen() }


            composable("account") {

                //FACTORY Y REPOSITORY
                val factory = remember(profileRepository) {
                    ProfileViewModelFactory(profileRepository)
                }

                //VIEWMODEL
                val profileViewModel: ProfileViewModel = viewModel(
                    factory = factory
                )

                // MOSTRAR PANTALLA
                AccountScreen(
                    viewModel = profileViewModel
                )
            }


            composable("product") {
                ProductScreen()
            }
        }
    }
}
