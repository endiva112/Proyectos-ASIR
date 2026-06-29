package com.spinandgg.app.ui.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.spinandgg.app.ui.logica.GestorUsuarios


@Composable
fun CargarLogin(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = com.spinandgg.app.R.drawable.fotologin),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
                .background(Color.Black),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = com.spinandgg.app.R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = (-40).dp)
                )
                Text(
                    text = "Email o número de móvil",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp, start = 10.dp)
                        .offset(y = (-60).dp)
                )
                TextField(
                    value = username,
                    onValueChange = { newValue ->
                        // Filtrar saltos de línea y caracteres de nueva línea
                        username = newValue.replace("\n", "").replace("\r", "")
                    },
                    label = { Text("Usuario") },
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-60).dp)
                )
                Text(
                    text = "Contraseña",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 4.dp, start = 10.dp)
                        .offset(y = (-60).dp)
                )
                TextField(
                    value = password,
                    onValueChange = { newValue ->
                        // Filtrar saltos de línea y caracteres de nueva línea
                        password = newValue.replace("\n", "").replace("\r", "")
                    },
                    label = { Text("Contraseña") },
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-60).dp)
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.offset(y = (-55).dp)
                    )
                }

                Button(
                    onClick = {
                        var usuarioActivo = GestorUsuarios.login(username, password)
                        if ((password.isNotEmpty()) && (username.isNotEmpty())  ){
                            if (usuarioActivo != null) {
                                GestorUsuarios.crearSesion(usuarioActivo)
                                navController.navigate("rutaHome") {
                                    launchSingleTop = true
                                }
                            } else {
                                errorMessage = "Usuario o contraseña incorrectos"
                            }
                        }
                        if ((usuarioActivo?.password == null) || (usuarioActivo?.username == null) ){
                            errorMessage = "Usuario o Contraseña sin rellenar"
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .offset(y = (-35).dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007ACC),
                        contentColor = Color.White
                    )
                ) {
                    Text("CONECTARSE")
                }
                Text(
                    text = "¿No tienes una cuenta?",
                    color = Color.White,
                    modifier = Modifier.offset(y = (-25).dp)
                )
                Text(
                    text = "Créate una.",
                    color = Color.Yellow,
                    modifier = Modifier.offset(y = (-25).dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Los juegos de azar están prohibidos para menores",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}