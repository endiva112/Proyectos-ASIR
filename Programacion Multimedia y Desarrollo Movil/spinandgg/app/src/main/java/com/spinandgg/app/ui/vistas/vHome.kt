package com.spinandgg.app.ui.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.spinandgg.app.ui.componentes.AgregarBarraInformativa
import com.spinandgg.app.ui.componentes.AgregarBarraNavegacion
import com.spinandgg.app.ui.componentes.AgregarCabecera
import com.spinandgg.app.ui.componentes.AgregarTarjetaApuesta
import com.spinandgg.app.ui.logica.GestorApuestas

@Composable
fun CargarHome(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AgregarCabecera(modifier = Modifier.weight(1f), navController)

        AgregarBarraInformativa(modifier = Modifier.weight(0.6f), "RESULTADOS DE ÚLTIMOS PARTIDOS", Color.White,Color(0xFF000000))

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
            items(GestorApuestas.apuestasExistentes) { apuesta ->
                AgregarTarjetaApuesta(
                    apuesta = apuesta,
                    onClickEquipoLocal = {
                        apuestaSeleccionada -> //logica
                    },
                    onClickEmpate = {
                            apuestaSeleccionada -> //logica
                    },
                    onClickEquipoVisitante = {
                            apuestaSeleccionada -> //logica
                    },
                )
            }
        }

        AgregarBarraNavegacion(modifier = Modifier.weight(0.7f), navController)
    }
}
