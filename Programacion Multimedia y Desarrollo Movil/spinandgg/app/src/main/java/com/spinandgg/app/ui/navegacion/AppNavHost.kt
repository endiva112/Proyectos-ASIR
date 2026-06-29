package com.spinandgg.app.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.spinandgg.app.ui.vistas.CargarHome
import com.spinandgg.app.ui.vistas.CargarListadoDeApuestas
import com.spinandgg.app.ui.vistas.CargarLogin
import com.spinandgg.app.ui.vistas.CargarPerfil
import com.spinandgg.app.ui.vistas.CargarSettings


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "rutaLogin"
    ) {
        composable("rutaLogin") { CargarLogin(navController) }

        composable("rutaHome") { CargarHome(navController) }
        composable("rutaListadoDeApuestas") { CargarListadoDeApuestas(navController) }
        composable("rutaSettings") { CargarSettings(navController) }
        composable("rutaPerfil") { CargarPerfil(navController) }
    }
}
