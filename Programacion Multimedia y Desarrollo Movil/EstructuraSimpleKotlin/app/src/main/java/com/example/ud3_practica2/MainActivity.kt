package com.example.ud3_practica2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ud3_practica2.ui.theme.UD3Practica2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutFilasColumnas()
        }
    }
}

@Preview
@Composable
fun LayoutFilasColumnas() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 44.dp, end = 10.dp, bottom = 10.dp, start = 10.dp,)
    ) {
        Column () {
            Text(
                text = "Filas"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column () {
                for (i in 1..4) {
                    Row (
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                    ) {
                        for (x in 1..4) {
                            Text(text = "Texto ${i}")
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column () {//Columna
            Text(text = "Columnas")
            Spacer(modifier = Modifier.height(20.dp))
            Row () {
                for (i in 1..4) {
                    Column (
                        modifier = Modifier
                            .padding(end = 16.dp)
                    ) {
                        for (x in 1..4) {
                            Text(text = "Texto ${i}")
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}