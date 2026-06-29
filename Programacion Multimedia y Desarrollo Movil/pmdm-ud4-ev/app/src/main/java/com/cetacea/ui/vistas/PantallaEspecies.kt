package com.cetacea.ui.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cetacea.ui.modelos.Especie

@Composable
fun PantallaEspecies(navController: NavHostController, especies: List<Especie>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Cabecera()
        Cuerpo(
            modifier = Modifier.weight(1f),
            especies = especies,
            onTarjetaClick = { especie ->
                navController.navigate("detalle/${especie.nombre}")
            }
        )
        BarraNavegacion(navController)
    }
}

@Composable
fun Cabecera() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0A1128))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Cetacea",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC6DAF1)
        )
    }
}

@Composable
fun Cuerpo(
    modifier: Modifier = Modifier,
    especies: List<Especie>,
    onTarjetaClick: (Especie) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF007EA7),
                        Color(0xFF034078),
                        Color(0xFF001F54),
                    )
                )
            ),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(especies.size) { index ->
            Tarjeta(
                especie = especies[index],
                onClick = { onTarjetaClick(especies[index]) }
            )
        }
    }
}

@Composable
fun BarraNavegacion(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0A1128))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .clickable {
                    navController.navigate("especies") {
                        // Evita crear múltiples copias en el stack
                        popUpTo("especies") { inclusive = true }
                        launchSingleTop = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Especies",
                color = Color(0xFFC6DAF1),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .clickable {
                    navController.navigate("documentales") {
                        popUpTo("especies") { inclusive = false }
                        launchSingleTop = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Documentales",
                color = Color(0xFFC6DAF1),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun Tarjeta(especie: Especie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF0A1128),
                shape = RoundedCornerShape(30.dp)
            )
            .clickable { onClick() }
            .padding(20.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = especie.imagenRes),
            contentDescription = especie.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = especie.nombre,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC6DAF1),
            modifier = Modifier.fillMaxWidth()
        )
    }
}