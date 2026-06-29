package com.cetacea.ui.vistas

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cetacea.R
import com.cetacea.ui.modelos.Especie
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.style.TextAlign


@Composable
fun PantallaDetalle(especie: Especie, navController: NavHostController) {
    val context = LocalContext.current

    // MediaPlayer para el audio
    val player = remember {
        MediaPlayer.create(context, especie.audioRes)
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                if (player.isPlaying) {
                    player.stop()
                }
            } catch (_: IllegalStateException) { }
            player.release()
        }
    }



    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF007EA7),
                        Color(0xFF034078),
                        Color(0xFF001F54),
                    )
                )
            )
            .padding(24.dp, 16.dp)
    ) {
        // Botón para volver
        Text(
            text = "← Volver",
            color = Color(0xFFC6DAF1),
            fontSize = 16.sp,
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Imagen local
        Image(
            painter = painterResource(id = especie.imagenRes),
            contentDescription = especie.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Nombre
        Text(
            text = especie.nombre,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC6DAF1)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción
        Text(
            text = especie.descripcion,
            //textAlign = TextAlign.Justify, //A mi el justify me gusta, comentado por Rafa
            fontSize = 18.sp,
            color = Color(0xFFC6DAF1),
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Imagen remota con fallback a local
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(especie.imagenUrl)
                .crossfade(true)
                .build(),
            contentDescription = "${especie.nombre} imagen remota",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(id = especie.imagenRes), // Fallback a imagen local
            placeholder = painterResource(id = especie.imagenRes)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Reproductor de audio con forma de píldora
        ReproductorAudio(player = player)
    }
}

@Composable
fun ReproductorAudio(player: MediaPlayer) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = Color(0xFF0A1128),
                shape = RoundedCornerShape(35.dp) // Forma de píldora
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón Play
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFF007EA7),
                    shape = CircleShape
                )
                .clickable {
                    if (!player.isPlaying) {
                        player.start()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Play",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Botón Pause
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFF1E3A5F),
                    shape = CircleShape
                )
                .clickable {
                    if (player.isPlaying) {
                        player.pause()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pause",
                color = Color(0xFFC6DAF1),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Botón Reiniciar
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color(0xFF1E3A5F),
                    shape = CircleShape
                )
                .clickable {
                    player.seekTo(0)
                    if (player.isPlaying) {
                        player.pause()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Reset",
                color = Color(0xFFC6DAF1),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}