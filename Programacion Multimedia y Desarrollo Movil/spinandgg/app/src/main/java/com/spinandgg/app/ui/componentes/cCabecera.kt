package com.spinandgg.app.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.spinandgg.app.R
import com.spinandgg.app.ui.logica.GestorUsuarios.obtenerIdDesdeNombre
import com.spinandgg.app.ui.logica.GestorUsuarios.usuarioActivo

@Composable
fun AgregarCabecera(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF000000))
            .padding(16.dp, 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.header_icon),
            contentDescription = "Logo principal",
            modifier = Modifier
                .align(Alignment.Center)
                .matchParentSize()
        )

        Image(
            painter = painterResource(
                id = usuarioActivo?.userImg?.let {
                    obtenerIdDesdeNombre(it)
                } ?: R.drawable.user_ico
            ),
            contentDescription = "Perfil de usuario",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(50.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .clickable {
                    navController.navigate("rutaPerfil") {
                        launchSingleTop = true
                    }
                }
        )
    }
}