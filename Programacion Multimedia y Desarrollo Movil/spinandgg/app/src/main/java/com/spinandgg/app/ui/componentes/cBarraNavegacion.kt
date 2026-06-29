package com.spinandgg.app.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.spinandgg.app.R

@Composable
fun AgregarBarraNavegacion(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono Home
        Image(
            painter = painterResource(id = R.drawable.house_ico),
            contentDescription = "Home",
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    navController.navigate("rutaHome") {
                        launchSingleTop = true
                    }
                }
        )

        // Icono Buscar
        Image(
            painter = painterResource(id = R.drawable.search_ico),
            contentDescription = "Buscar",
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    navController.navigate("rutaListadoDeApuestas") {
                        launchSingleTop = true
                    }
                }
        )

        // Icono Ajustes
        Image(
            painter = painterResource(id = R.drawable.gear_ico),
            contentDescription = "Ajustes",
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    navController.navigate("rutaSettings") {
                        launchSingleTop = true
                    }
                }
        )
    }
}