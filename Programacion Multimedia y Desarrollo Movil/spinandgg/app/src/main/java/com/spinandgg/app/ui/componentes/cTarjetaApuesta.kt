package com.spinandgg.app.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spinandgg.app.ui.logica.GestorApuestas.obtenerIdDesdeNombre
import com.spinandgg.app.ui.modelos.Apuesta

@Composable
fun AgregarTarjetaApuesta(
    apuesta: Apuesta,
    onClickEquipoLocal: (Apuesta) -> Unit = {},
    onClickEmpate: (Apuesta) -> Unit = {},
    onClickEquipoVisitante: (Apuesta) -> Unit = {} ,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2639C6),
                            Color.Black
                        )
                    ),
                    shape = RoundedCornerShape(25.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(25.dp)
                )
        ) {
            Text(
                text = apuesta.eventDate,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp)
            )

            // Contenedor de imagen y botones
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 35.dp, end = 10.dp, bottom = 10.dp)
            ) {
                // Imagen de fondo
                Image(
                    painter = painterResource(id = obtenerIdDesdeNombre(apuesta.betImg)),
                    contentDescription = "Imagen del partido ${apuesta.teamAName} vs ${apuesta.teamBName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )

                // Botones de apuestas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Botón Equipo Local
                    BotonApuesta(
                        equipo = apuesta.teamAName,
                        cuota = apuesta.betOddTeamA.toString(),
                        onClick = { onClickEquipoLocal(apuesta) },
                        modifier = Modifier.weight(1f)
                    )

                    // Botón Empate
                    BotonApuesta(
                        equipo = "Empate",
                        cuota = apuesta.betOddDraw.toString(),
                        onClick = { onClickEmpate(apuesta) },
                        modifier = Modifier.weight(1f)
                    )

                    // Botón Equipo Visitante
                    BotonApuesta(
                        equipo = apuesta.teamBName,
                        cuota = apuesta.betOddTeamB.toString(),
                        onClick = { onClickEquipoVisitante(apuesta) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BotonApuesta(
    equipo: String,
    cuota: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = modifier.height(60.dp),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = equipo,
                color = Color.Black,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = cuota,
                color = Color(0xFF2639C6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}