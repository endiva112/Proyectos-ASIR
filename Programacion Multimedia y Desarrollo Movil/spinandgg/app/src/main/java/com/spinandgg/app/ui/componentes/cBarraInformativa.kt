package com.spinandgg.app.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgregarBarraInformativa(
    modifier: Modifier = Modifier,
    infoText: String,
    fontColor: Color,
    bgColor: Color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = infoText,
            color = fontColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
