package com.example.vehicledashboard.presentation.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vehicledashboard.presentation.VehicalDetail.VehicleDetailScreen
import com.example.vehicledashboard.presentation.VehicalList.VehicleListScreen


object Routes {
    const val LIST = "vehicles"
    const val DETAIL = "vehicle/{id}"
    fun detail(id: String) = "vehicle/$id"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            VehicleListScreen(
                onVehicleClick = { id -> navController.navigate(Routes.detail(id)) }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            VehicleDetailScreen(
                vehicleId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}