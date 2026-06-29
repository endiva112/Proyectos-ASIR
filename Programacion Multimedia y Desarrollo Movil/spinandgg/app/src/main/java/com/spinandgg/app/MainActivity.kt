package com.spinandgg.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.spinandgg.app.ui.logica.GestorApuestas

import com.spinandgg.app.ui.navegacion.AppNavHost
import com.spinandgg.app.ui.theme.SpinAndGGTheme
import com.spinandgg.app.ui.logica.GestorUsuarios

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpinAndGGTheme {
                GestorUsuarios.cargarUsuarios()
                GestorApuestas.cargarApuestas()
                AppNavHost()
            }
        }
    }
}