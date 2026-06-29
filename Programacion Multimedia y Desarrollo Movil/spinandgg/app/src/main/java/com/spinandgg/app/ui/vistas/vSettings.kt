package com.spinandgg.app.ui.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spinandgg.app.ui.componentes.AgregarBarraInformativa
import com.spinandgg.app.ui.componentes.AgregarBarraNavegacion
import com.spinandgg.app.ui.componentes.AgregarCabecera
import com.spinandgg.app.ui.logica.GestorUsuarios


@Composable
fun CargarSettings(navController: NavHostController) {
    var email by remember { mutableStateOf(GestorUsuarios.usuarioActivo?.email ?: "???") }
    var password by remember { mutableStateOf(GestorUsuarios.usuarioActivo?.password ?: "???") }

    var updateMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0x80273BCC))
    ) {
        AgregarCabecera(modifier = Modifier.weight(1f), navController)

        AgregarBarraInformativa(modifier = Modifier.weight(0.6f), "MI CUENTA", Color.Black,Color(
            0xCCE4E4E4
        )
        )

        /*titulo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFEEEEEE))
        ) {
            Text(
                text = "Mi Cuenta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }*/

        //contenido
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7.7f)
                .padding(20.dp, 10.dp, 20.dp)
        ) {
            // Subtítulo
            Text(
                text = "Datos de contacto",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Campo Email
            Text(text = "Email")
            TextField(
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .background(Color(0xFFE0E0E0) ,RoundedCornerShape(10.dp)),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )

            // Campo Contraseña
            Text(text = "Contraseña")
            TextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(10.dp)),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )

            )

            Button(
                onClick = {
                    GestorUsuarios.usuarioActivo?.let { it.email = email }
                    GestorUsuarios.usuarioActivo?.let { it.password = password }

                    updateMessage = "Campos actualizados correctamente"
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF0D0335)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .height(50.dp)
                    .offset(y = (40).dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Actualizar",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            if (updateMessage.isNotEmpty()) {
                Text(
                    text = updateMessage,
                    color = Color.Green,
                    modifier = Modifier.offset(y = (-55).dp)
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Cerrar Sesión",
                    color = Color(0xFF0D0335),
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(y = (60).dp)
                        .clickable {
                            GestorUsuarios.usuarioActivo = null //Desloguear al usuario
                            navController.navigate("rutaLogin") {
                                launchSingleTop = true
                            }
                        }
                )
            }
        }
        AgregarBarraNavegacion(modifier = Modifier.weight(0.7f), navController)
    }
}