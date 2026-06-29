package com.example.u3p3pantalla_de_perfil_completa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallaPerfil()
        }
    }
}

@Composable
fun PantallaPerfil() {
    var nombre by remember { mutableStateOf("nombre de usuario") }
    var correo by remember { mutableStateOf("correo electrónico") }

    var ocupacion = "Analista de datos Senior"
    var textoDescriptivo = "Especialista con 6 años de experiencia, trabajando en Google actualmente"


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .clip(CircleShape)
                    .matchParentSize()
                    .zIndex(0f),
                contentScale = ContentScale.Crop,
            )

            // Texto encima de la imagen
            Text(
                text = correo,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .zIndex(1f)
                    .shadow(30.dp, CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(6.dp, 2.dp)
            )
        }

        Text(
            text = nombre,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        )

        Text(
            text = ocupacion,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(0.dp, 2.dp)
        )

        Text(
            text = textoDescriptivo,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp, 10.dp, 10.dp, 30.dp)
        )

        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            StatItem("125", "Seguidores")
            StatItem("342", "Likes")
            StatItem("18", "Proyectos")
        }

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 10.dp)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 20.dp)
        )

        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(bottom = 40.dp)
        ) {
            Button(
                onClick = { /* TODO: acción */ },
                Modifier.weight(1f)
            ) {Text("Aceptar")}

            OutlinedButton(
                onClick = { /* TODO: acción */ },
                Modifier.weight(1f)
            ) {Text("Cancelar")}
        }

        Box (
            Modifier.size(200.dp)
        ) {
            Text(
                text = "TL",
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Text(
                text = "TR",
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )
            Text(
                text = "BL",
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
            Text(
                text = "BR",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
            Text(
                text = "CENTER",
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun StatItem(valor: String, etiqueta: String) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = valor,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = etiqueta,
            fontSize = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MaterialTheme() {
    PantallaPerfil()
}