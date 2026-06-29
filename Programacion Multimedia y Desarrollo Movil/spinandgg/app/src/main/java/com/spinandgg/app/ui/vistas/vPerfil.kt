package com.spinandgg.app.ui.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.spinandgg.app.R
import com.spinandgg.app.ui.componentes.AgregarBarraNavegacion
import com.spinandgg.app.ui.componentes.AgregarCabecera
import com.spinandgg.app.ui.logica.GestorUsuarios
import com.spinandgg.app.ui.logica.GestorUsuarios.obtenerIdDesdeNombre
import com.spinandgg.app.ui.logica.GestorUsuarios.usuarioActivo

@Composable
fun CargarPerfil(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0335))
    ) {
        // Cabecera
        AgregarCabecera(modifier = Modifier.weight(1f), navController)

        // Contenido del perfil
        Column(
            modifier = Modifier
                .weight(7.7f)
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Foto de perfil
            Image(
                painter = painterResource(
                    id = usuarioActivo?.userImg?.let {
                        obtenerIdDesdeNombre(it)
                    } ?: R.drawable.user_ico
                ),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre de usuario
            Text(
                text = GestorUsuarios.usuarioActivo?.username ?: "???",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Text(
                text = GestorUsuarios.usuarioActivo?.email ?: "???",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Información del usuario
            InfoBox(label = "Nombre completo", value = GestorUsuarios.usuarioActivo?.realname ?: "???")
            Spacer(modifier = Modifier.height(16.dp))
            InfoBox(label = "Teléfono", value = GestorUsuarios.usuarioActivo?.telephone ?: "???")
            Spacer(modifier = Modifier.height(16.dp))
            InfoBox(label = "Fecha de registro", value = GestorUsuarios.usuarioActivo?.logInDate ?: "???")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate("rutaHome") {
                        popUpTo("rutaHome") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2639C6),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Volver a la App",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        AgregarBarraNavegacion(modifier = Modifier.weight(0.7f), navController)
    }
}

@Composable
private fun InfoBox(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}