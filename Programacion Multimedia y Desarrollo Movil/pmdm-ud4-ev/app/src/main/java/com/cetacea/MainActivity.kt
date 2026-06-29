package com.cetacea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cetacea.ui.datos.listaEspecies
import com.cetacea.ui.datos.listaDocumentales
import com.cetacea.ui.vistas.PantallaDetalle
import com.cetacea.ui.vistas.PantallaEspecies
import com.cetacea.ui.vistas.PantallaDocumentales

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CetaceaApp()
        }
    }
}

@Composable
fun CetaceaApp() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "especies"
        ) {
            composable("especies") {
                PantallaEspecies(navController, listaEspecies)
            }

            composable("documentales") {
                PantallaDocumentales(navController, listaDocumentales)
            }

            composable("detalle/{nombreEspecie}") { backStackEntry ->
                val nombreEspecie = backStackEntry.arguments?.getString("nombreEspecie") ?: ""
                val especie = listaEspecies.find { it.nombre == nombreEspecie }

                if (especie != null) {
                    PantallaDetalle(especie, navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CetaceaAppPreview() {
    CetaceaApp()
}