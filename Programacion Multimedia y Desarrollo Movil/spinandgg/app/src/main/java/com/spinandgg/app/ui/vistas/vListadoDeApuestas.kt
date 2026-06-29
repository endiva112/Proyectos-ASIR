package com.spinandgg.app.ui.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.spinandgg.app.ui.componentes.AgregarTarjetaApuesta

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import com.spinandgg.app.ui.componentes.AgregarBarraBusqueda
import com.spinandgg.app.ui.componentes.AgregarBarraNavegacion
import com.spinandgg.app.ui.componentes.AgregarCabecera
import com.spinandgg.app.ui.logica.GestorApuestas

@Composable
fun CargarListadoDeApuestas(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    // Filtrar apuestas según la búsqueda
    val apuestasFiltradas = if (searchQuery.isEmpty()) {
        GestorApuestas.apuestasExistentes
    } else {
        GestorApuestas.apuestasExistentes.filter { apuesta ->
            apuesta.teamAName.contains(searchQuery, ignoreCase = true) ||
                    apuesta.teamBName.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AgregarCabecera(modifier = Modifier.weight(1f), navController)

        AgregarBarraBusqueda(
            modifier = Modifier.weight(0.6f),
            onSearchChange = { query ->
                searchQuery = query
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(7.7f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF110545),
                            Color(0xFF000000)
                        )
                    )
                )
        ) {
            items(apuestasFiltradas) { apuesta ->
                AgregarTarjetaApuesta(
                    apuesta = apuesta,
                    onClickEquipoLocal = { apuestaSeleccionada ->
                        //logica
                    },
                    onClickEmpate = { apuestaSeleccionada ->
                        //logica
                    },
                    onClickEquipoVisitante = { apuestaSeleccionada ->
                        //logica
                    },
                )
            }
        }

        AgregarBarraNavegacion(modifier = Modifier.weight(0.7f), navController)
    }
}