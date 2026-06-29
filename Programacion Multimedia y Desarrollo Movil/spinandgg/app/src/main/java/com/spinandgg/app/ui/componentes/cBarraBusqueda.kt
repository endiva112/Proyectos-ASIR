package com.spinandgg.app.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spinandgg.app.R

@Composable
fun AgregarBarraBusqueda(
    modifier: Modifier = Modifier,
    onSearchChange: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0D0335))
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Icono de búsqueda
            Image(
                painter = painterResource(id = R.drawable.search_ico),
                contentDescription = "Buscar",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(20.dp)
            )

            BasicTextField(
                value = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                    onSearchChange(newValue)
                },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(Color(0xFF2639C6)),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchText.isEmpty()) {
                            Text(
                                text = "Buscar apuestas...",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}